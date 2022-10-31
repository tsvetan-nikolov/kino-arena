package com.kinoarena.kinoarena.model.DAOs;

import com.kinoarena.kinoarena.model.DTOs.movie.MovieProgramDTO;
import com.kinoarena.kinoarena.model.DTOs.projection.response.ProjectionInfoDTO;
import com.kinoarena.kinoarena.model.DTOs.projection_type.ProjectionTypeInfoDTO;
import com.kinoarena.kinoarena.model.entities.AgeRestriction;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectionDAO {

    private final JdbcTemplate jdbcTemplate;

    public List<ProjectionInfoDTO> filterProjections(String cinema, String movie, LocalDate date) {
        String sql = String.format("SELECT p.id AS projectionId, p.start_time AS startTime, p.date AS date, " +
                "pt.id AS projectionTypeId, pt.type AS projectionType, m.id AS movieId, " +
                "m.name AS movieName, m.premiere AS premiere, m.is_dubbed AS isDubbed, " +
                "ar.id AS ageRestrictionId, ar.category AS ageRestriction, ar.age AS age " +
                "FROM projections AS p JOIN halls AS h ON (h.id = p.hall_id) " +
                "JOIN cinemas AS c ON (c.id = h.cinema_id) " +
                "JOIN movies AS m ON (m.id = p.movie_id) " +
                "JOIN projection_types AS pt ON (pt.id = p.projection_type_id) " +
                "JOIN age_restrictions AS ar ON (ar.id = m.age_restriction_id) " +
                "WHERE c.name = \"%s\" AND m.name = \"%s\" AND p.date = \"%s\" " +
                "ORDER BY m.name, p.date, pt.type, p.start_time ASC;", cinema, movie, date.toString());


        return projectionsMapRows(sql);
    }

    public List<ProjectionInfoDTO> getBrandProjections(String brand) {
        String sql = String.format("SELECT p.id AS projectionId, p.start_time AS startTime, p.date AS date, " +
                "pt.id AS projectionTypeId, pt.type AS projectionType, m.id AS movieId, " +
                "m.name AS movieName, m.premiere AS premiere, m.is_dubbed AS isDubbed, " +
                "ar.id AS ageRestrictionId, ar.category AS ageRestriction, ar.age AS age " +
                "FROM projections AS p JOIN halls AS h ON (h.id = p.hall_id) " +
                "JOIN cinemas AS c ON (c.id = h.cinema_id) " +
                "JOIN movies AS m ON (m.id = p.movie_id) " +
                "JOIN projection_types AS pt ON (pt.id = p.projection_type_id) " +
                "JOIN age_restrictions AS ar ON (ar.id = m.age_restriction_id) " +
                "WHERE pt.type = \"%s\" " +
                "ORDER BY m.name, p.date, pt.type, p.start_time ASC;", brand);

        return projectionsMapRows(sql);
    }

    public List<ProjectionInfoDTO> filterBrandProjections(String brand, String cinema) {
        String sql = String.format("SELECT p.id AS projectionId, p.start_time AS startTime, p.date AS date, " +
                "pt.id AS projectionTypeId, pt.type AS projectionType, m.id AS movieId, m.name AS movieName, m.is_dubbed AS isDubbed, " +
                "m.premiere AS premiere, ar.id AS ageRestrictionId, ar.category AS ageRestriction, ar.age AS age " +
                "FROM cinemas AS c JOIN halls AS h ON (c.id = h.cinema_id) " +
                "JOIN projections AS p ON (h.id = p.hall_id) " +
                "JOIN movies AS m ON (m.id = p.movie_id) " +
                "JOIN projection_types AS pt ON (pt.id = p.projection_type_id) " +
                "JOIN age_restrictions AS ar ON (ar.id = m.age_restriction_id) " +
                "WHERE pt.type = \"%s\" AND c.name = \"%s\" " +
                "ORDER BY m.name, p.date, pt.type, p.start_time ASC;", brand, cinema);

        return projectionsMapRows(sql);
    }

    public List<ProjectionInfoDTO> projectionsMapRows(String sql) {
        List<ProjectionInfoDTO> projections = jdbcTemplate.query(sql, new RowMapper<ProjectionInfoDTO>() {
            @Override
            public ProjectionInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                return setProjectionValues(rs);
            }
        });
        return projections;
    }

    public ProjectionInfoDTO setProjectionValues(ResultSet rs) throws SQLException {

        int projectionTypeId = rs.getInt("projectionTypeId");
        String projectionType = rs.getString("projectionType");
        ProjectionTypeInfoDTO pt = new ProjectionTypeInfoDTO(projectionTypeId, projectionType);

        int projectionId = rs.getInt("projectionId");
        LocalTime projectionTime = rs.getTime("startTime").toLocalTime();
        LocalDate projectionDate = rs.getDate("date").toLocalDate();

        MovieProgramDTO movie = setMovieValues(rs);

        return new ProjectionInfoDTO(projectionId, projectionTime, projectionDate, pt, movie);
    }

    private MovieProgramDTO setMovieValues(ResultSet rs) throws SQLException {

        int movieId = rs.getInt("movieId");
        String movieName = rs.getString("movieName");
        LocalDate premiere = rs.getDate("premiere").toLocalDate();
        AgeRestriction ageRestr = new AgeRestriction(rs.getInt("ageRestrictionId"),
                rs.getString("ageRestriction"), rs.getInt("age"));
        boolean isDubbed = rs.getBoolean("isDubbed");

        return new MovieProgramDTO(movieId, movieName, premiere, ageRestr, isDubbed);
    }

}
