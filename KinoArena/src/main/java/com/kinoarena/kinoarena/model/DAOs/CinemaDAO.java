package com.kinoarena.kinoarena.model.DAOs;

import com.kinoarena.kinoarena.model.DTOs.cinema.CinemaInfoResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.city.CityInfoResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.projection.response.ProjectionInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CinemaDAO {

    private final JdbcTemplate jdbcTemplate;
    private final ProjectionDAO projectionDAO;

    public List<ProjectionInfoDTO> getProgram(int cid) {

        String sql = String.format("SELECT p.id AS projectionId, p.start_time AS startTime, p.date AS date, " +
                "pt.id AS projectionTypeId, pt.type AS projectionType, m.id AS movieId, " +
                "m.name AS movieName, m.premiere AS premiere, m.is_dubbed AS isDubbed, " +
                "ar.id AS ageRestrictionId, ar.category AS ageRestriction, ar.age AS age " +
                "FROM projections AS p JOIN halls AS h ON (h.id = p.hall_id) " +
                "JOIN cinemas AS c ON (c.id = h.cinema_id) " +
                "JOIN movies AS m ON (m.id = p.movie_id) " +
                "JOIN projection_types AS pt ON (pt.id = p.projection_type_id) " +
                "JOIN age_restrictions AS ar ON (ar.id = m.age_restriction_id) " +
                "WHERE c.id = %d " +
                "ORDER BY m.name, p.date, pt.type, p.start_time ASC;", cid);


        List<ProjectionInfoDTO> projections = projectionDAO.projectionsMapRows(sql);

        return projections;
    }

    public List<CinemaInfoResponseDTO> filterCinemasByCity(String cityName) {
        String sql = String.format("SELECT cinemas.id AS cinemaId, cinemas.name AS name, cinemas.address AS address, " +
                "cities.id AS cityId, cities.name AS city " +
                "FROM cinemas " +
                "JOIN cities ON (cinemas.city_id=cities.id) " +
                "WHERE cities.name = \"%s\";", cityName);

        List<CinemaInfoResponseDTO> cinemas = jdbcTemplate.query(sql, new RowMapper<CinemaInfoResponseDTO>() {
            @Override
            public CinemaInfoResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                return setCinemaValues(rs);
            }
        });

        return cinemas;
    }

    private CinemaInfoResponseDTO setCinemaValues(ResultSet rs) throws SQLException {
        int cinemaID = rs.getInt("cinemaId");
        String cinemaName = rs.getString("name");
        String address = rs.getString("address");
        CityInfoResponseDTO city = new CityInfoResponseDTO(rs.getInt("cityId"), rs.getString("city"));

        return new CinemaInfoResponseDTO(cinemaID, cinemaName, address, city);
    }
}
