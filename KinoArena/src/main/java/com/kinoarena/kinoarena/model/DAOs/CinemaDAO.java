package com.kinoarena.kinoarena.model.DAOs;

import com.kinoarena.kinoarena.model.DTOs.age_restriction.AgeRestrictionForMovieDTO;
import com.kinoarena.kinoarena.model.DTOs.movie.MovieProgramDTO;
import com.kinoarena.kinoarena.model.DTOs.projection.ProjectionInfoDTO;
import com.kinoarena.kinoarena.model.DTOs.projection_type.ProjectionTypeInfoDTO;
import com.kinoarena.kinoarena.model.entities.Movie;
import com.kinoarena.kinoarena.model.entities.Projection;
import com.kinoarena.kinoarena.model.entities.ProjectionType;
import com.kinoarena.kinoarena.model.repositories.MovieRepository;
import com.kinoarena.kinoarena.model.repositories.ProjectionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CinemaDAO {

    private final JdbcTemplate jdbcTemplate;

//todo refactoring and make jdbcTemplate work
    public List<ProjectionInfoDTO> getProgram(int cid) {
//        String sql = String.format("SELECT p.id AS projectionId, p.start_time AS projectionStart, p.date AS projectionDate, pt.id AS projectionTypeId, " +
//                "pt.type AS projectionType, m.id AS movieId, m.name AS movieName, m.is_dubbed AS isDubbed, m.premiere AS moviePremiere, " +
//                "ar.category AS ageRestriction " +
//                "FROM cinemas AS c JOIN halls AS h ON (c.id = h.cinema_id) " +
//                "JOIN projections AS p ON (h.id = p.hall_id) " +
//                "JOIN movies AS m ON (m.id = p.movie_id) " +
//                "JOIN projection_types AS pt ON (pt.id = p.projection_type_id) " +
//                "JOIN age_restrictions AS ar ON (ar.id = m.age_restriction_id) " +
//                "WHERE c.id = %d " +
//                "GROUP BY m.name, p.date, pt.type, p.start_time", cid);
        String sql = String.format("SELECT p.id AS projectionId, p.start_time AS startTime, p.date AS date, " +
                "pt.id AS projectionTypeId, pt.type AS projectionType, m.id AS movieId, " +
                "m.name AS movieName, m.premiere AS premiere, m.is_dubbed AS isDubbed, " +
                "ar.id AS ageRestrictionId, ar.category AS ageRestriction " +
                "FROM projections AS p JOIN halls AS h ON (h.id = p.hall_id) " +
                "JOIN cinemas AS c ON (c.id = h.cinema_id) " +
                "JOIN movies AS m ON (m.id = p.movie_id) " +
                "JOIN projection_types AS pt ON (pt.id = p.projection_type_id) " +
                "JOIN age_restrictions AS ar ON (ar.id = m.age_restriction_id) " +
                "WHERE c.id = %d " +
                "ORDER BY m.name, p.date, pt.type, p.startTime ASC;", cid);

        List<ProjectionInfoDTO> projections = jdbcTemplate.query(sql, new RowMapper<ProjectionInfoDTO>() {
            @Override
            public ProjectionInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                ProjectionInfoDTO p = setProjectionValues(rs);
                return p;
            }
        });

        return projections;
    }

    public ProjectionInfoDTO setProjectionValues(ResultSet rs) throws SQLException {
        ProjectionInfoDTO projection = new ProjectionInfoDTO();

        int projectionTypeId = rs.getInt("projectionTypeId");
        String projectionType = rs.getString("projectionType");
        ProjectionTypeInfoDTO pt = new ProjectionTypeInfoDTO(projectionTypeId, projectionType);

        int projectionId = rs.getInt("projectionId");
        LocalTime projectionTime = rs.getTime("startTime").toLocalTime();
        LocalDate projectionDate = rs.getDate("date").toLocalDate();

        MovieProgramDTO movie = setMovieValues(rs);

        projection.setId(projectionId);
        projection.setStartTime(projectionTime);
        projection.setDate(projectionDate);
        projection.setProjectionType(pt);
        projection.setMovie(movie);

        return projection;
    }

    private MovieProgramDTO setMovieValues(ResultSet rs) throws SQLException {
        MovieProgramDTO movie = new MovieProgramDTO();

        int movieId = rs.getInt("movieId");
        String movieName = rs.getString("movieName");
        LocalDate premiere = rs.getDate("premiere").toLocalDate();
        AgeRestrictionForMovieDTO ageRestr = new AgeRestrictionForMovieDTO(rs.getInt("ageRestrictionId"),
                                                                            rs.getString("ageRestriction"));
        boolean isDubbed = rs.getBoolean("isDubbed");

        movie.setId(movieId);
        movie.setName(movieName);
        movie.setPremiere(premiere);
        movie.setAgeRestriction(ageRestr);
        movie.setDubbed(isDubbed);

        return movie;
    }

}
