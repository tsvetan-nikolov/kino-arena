package com.kinoarena.kinoarena.model.DAOs;

import com.kinoarena.kinoarena.model.DTOs.age_restriction.AgeRestrictionForMovieDTO;
import com.kinoarena.kinoarena.model.DTOs.movie.MovieProgramDTO;
import com.kinoarena.kinoarena.model.DTOs.projection.response.ProjectionInfoDTO;
import com.kinoarena.kinoarena.model.DTOs.projection_type.ProjectionTypeInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CinemaDAO {


    private final JdbcTemplate jdbcTemplate;

    public Map<String, MovieProgramDTO> getProgram(int cid) {
        String query = "SELECT p.id AS projectionId, " +
                "p.start_time AS projectionStart, " +
                "p.date AS projectionDate, " +
                "pt.id AS projectionTypeId, " +
                "pt.type AS projectionType, " +
                "m.id AS movieId, " +
                "m.name AS movieName, " +
                "m.is_dubbed AS isDubbed, " +
                "m.premiere AS moviePremiere, " +
                "ar.category AS ageRestriction " +
                "FROM cinemas AS c " +
                "JOIN halls AS h " +
                "ON (c.id = h.cinema_id) " +
                "JOIN projections AS p " +
                "ON (h.id = p.hall_id) " +
                "JOIN movies AS m " +
                "ON (m.id = p.movie_id) " +
                "JOIN projection_types AS pt " +
                "ON (pt.id = p.projection_type_id) " +
                "JOIN age_restrictions AS ar " +
                "ON (ar.id = m.age_restriction_id) " +
                "WHERE c.id = ? " +
                "GROUP BY m.name, p.date, pt.type, p.start_time";

        DataSource dataSource = this.jdbcTemplate.getDataSource();

        Map<String, MovieProgramDTO> program = new HashMap<>();

        if (dataSource != null) {
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, cid);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {

                    if (!program.containsKey(resultSet.getString("title"))) {
                        MovieProgramDTO movie = setMovieValues(resultSet);

                        program.put(resultSet.getString("title"), movie);
                    }

                    ProjectionInfoDTO projection = setProjectionValues(resultSet);

                    program.get(resultSet.getString("title")).getProjections().add(projection);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Failed connection" + e.getMessage());
            }
        }
        return program;
    }

    private ProjectionInfoDTO setProjectionValues(ResultSet rs) throws SQLException {
        ProjectionInfoDTO projection = new ProjectionInfoDTO();

        int projectionTypeId = rs.getInt("projectionTypeId");
        String projectionType = rs.getString("projectionType");
        ProjectionTypeInfoDTO pt = new ProjectionTypeInfoDTO(projectionTypeId, projectionType);

        int projectionId = rs.getInt("projectionId");
        LocalTime projectionTime = rs.getTime("projectionStart").toLocalTime();
        LocalDate projectionDate = rs.getDate("projectionDate").toLocalDate();

        projection.setId(projectionId);
        projection.setStartTime(projectionTime);
        projection.setDate(projectionDate);
        projection.setProjectionType(pt);

        return projection;
    }

    private MovieProgramDTO setMovieValues(ResultSet rs) throws SQLException {
        MovieProgramDTO movie = new MovieProgramDTO();

        int movieId = rs.getInt("movieId");
        String movieName = rs.getString("title");
        LocalDate premiere = rs.getDate("moviePremiere").toLocalDate();
        AgeRestrictionForMovieDTO ageRestr = new AgeRestrictionForMovieDTO(rs.getString("ageRestriction"));
        boolean isDubbed = rs.getBoolean("isDubbed");

        movie.setId(movieId);
        movie.setName(movieName);
        movie.setPremiere(premiere);
        movie.setAgeRestriction(ageRestr);
        movie.setDubbed(isDubbed);

        return movie;
    }

}
