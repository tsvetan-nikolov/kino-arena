package com.kinoarena.kinoarena.model.DAOs;

import com.kinoarena.kinoarena.model.DTOs.projection.ProjectionInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectionDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CinemaDAO cinemaDAO;

    public List<ProjectionInfoDTO> filterProjections(String cinema, String movie, LocalDate date) {
        String sql = String.format("SELECT p.id AS projectionId, p.start_time AS startTime, p.date AS date, " +
                "pt.id AS projectionTypeId, pt.type AS projectionType, m.id AS movieId, " +
                "m.name AS movieName, m.premiere AS premiere, m.is_dubbed AS isDubbed, " +
                "ar.id AS ageRestrictionId, ar.category AS ageRestriction " +
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
                "ar.id AS ageRestrictionId, ar.category AS ageRestriction " +
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
                "m.premiere AS premiere, ar.id AS ageRestrictionId, ar.category AS ageRestriction " +
                "FROM cinemas AS c JOIN halls AS h ON (c.id = h.cinema_id) " +
                "JOIN projections AS p ON (h.id = p.hall_id) " +
                "JOIN movies AS m ON (m.id = p.movie_id) " +
                "JOIN projection_types AS pt ON (pt.id = p.projection_type_id) " +
                "JOIN age_restrictions AS ar ON (ar.id = m.age_restriction_id) " +
                "WHERE pt.type = \"%s\" AND c.name = \"%s\" " +
                "ORDER BY m.name, p.date, pt.type, p.start_time ASC;", brand, cinema);

        return projectionsMapRows(sql);
    }
    private List<ProjectionInfoDTO> projectionsMapRows(String sql) {
        List<ProjectionInfoDTO> projections = jdbcTemplate.query(sql, new RowMapper<ProjectionInfoDTO>() {
            @Override
            public ProjectionInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                ProjectionInfoDTO p = cinemaDAO.setProjectionValues(rs);
                return p;
            }
        });
        return projections;
    }
}
