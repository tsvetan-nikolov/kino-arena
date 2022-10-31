package com.kinoarena.kinoarena.services;

import com.kinoarena.kinoarena.model.DTOs.cinema.CinemaInfoDTO;
import com.kinoarena.kinoarena.model.DTOs.movie.MovieInfoDTO;
import com.kinoarena.kinoarena.model.DTOs.projection.ProjectionWithCinemaDTO;
import com.kinoarena.kinoarena.model.DTOs.projection.ProjectionWithHallDTO;
import com.kinoarena.kinoarena.model.entities.Movie;
import com.kinoarena.kinoarena.model.entities.Projection;
import com.kinoarena.kinoarena.model.exceptions.NotFoundException;
import com.kinoarena.kinoarena.model.repositories.MovieRepository;
import com.kinoarena.kinoarena.model.repositories.ProjectionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;
    private final ProjectionRepository projectionRepository;

    public MovieInfoDTO getMovieInfo(int mid) {
        Optional<Movie> movie = movieRepository.findById(mid);

        if (movie.isPresent()) {
            Movie m = movie.get();

            MovieInfoDTO dto = modelMapper.map(m, MovieInfoDTO.class);

            List<ProjectionWithHallDTO> withHall = getProjections(dto);
            dto.setProjections(withHall.stream().map(p -> modelMapper.map(p, ProjectionWithCinemaDTO.class)).collect(Collectors.toList()));

            sortProjections(dto);

            return dto;
        } else {
            throw new NotFoundException("Movie is not found!");
        }
    }

    private void sortProjections(MovieInfoDTO dto) {
        Collections.sort(dto.getProjections(), new Comparator<ProjectionWithCinemaDTO>() {
            @Override
            public int compare(ProjectionWithCinemaDTO o1, ProjectionWithCinemaDTO o2) {
                if (o2.getCinema().getName().compareTo(o1.getCinema().getName()) == 0) {
                    if (o2.getDate().compareTo(o1.getDate()) == 0) {
                        if (o2.getProjectionType().getType().compareTo(o1.getProjectionType().getType()) == 0) {
                            if (o2.getStartTime().compareTo(o1.getStartTime()) == 0) {
                                return 0;
                            } else if (o2.getStartTime().compareTo(o1.getStartTime()) < 0) {
                                return -1;
                            } else {
                                return 1;
                            }
                        } else if (o2.getProjectionType().getType().compareTo(o1.getProjectionType().getType()) < 0) {
                            return -1;
                        } else {
                            return 1;
                        }
                    } else if (o2.getDate().compareTo(o1.getDate()) < 0) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else if (o2.getCinema().getName().compareTo(o1.getCinema().getName()) < 0) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }

    private List<ProjectionWithHallDTO> getProjections(MovieInfoDTO dto) {
        int movieId = dto.getId();

        List<Projection> projections = projectionRepository.findAllByMovieId(movieId);

        List<ProjectionWithHallDTO> withHall = projections.stream().map(p -> modelMapper.map(p, ProjectionWithHallDTO.class)).collect(Collectors.toList());
        withHall.stream().forEach(p -> p.setCinema(modelMapper.map(p.getHall().getCinema(), CinemaInfoDTO.class)));
        return withHall;
    }
}
