package com.kinoarena.kinoarena.services;

import com.kinoarena.kinoarena.model.DAOs.ProjectionDAO;
import com.kinoarena.kinoarena.model.DTOs.projection.FilterProjectionsRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.projection.ProjectionInfoDTO;
import com.kinoarena.kinoarena.model.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectionService {

    private final ProjectionDAO projectionDAO;
    public List<ProjectionInfoDTO> filterProjections(FilterProjectionsRequestDTO request) {
        String cinema = request.getCinema();
        String movie = request.getMovie();
        LocalDate date = request.getDate();

        if(cinema == null) {
            throw new BadRequestException("Trying to filter without cinema!");
        }
        if(movie == null) {
            throw new BadRequestException("Trying to filter without movie title!");
        }
        if(date == null) {
            throw new BadRequestException("Trying to filter without projection date!");
        }

        return projectionDAO.filterProjections(cinema, movie, date);
    }

    public List<ProjectionInfoDTO> getBrandProjections(String brand) {
        return projectionDAO.getBrandProjections(brand);
    }

    public List<ProjectionInfoDTO> filterBrandProjectionsByCinema(String brand, String cinema) {
        String[] cinemaNameSplit = cinema.split("-");
        String cinemaName = String.join(" ", cinemaNameSplit);
        return projectionDAO.filterBrandProjections(brand, cinemaName);
    }
}
