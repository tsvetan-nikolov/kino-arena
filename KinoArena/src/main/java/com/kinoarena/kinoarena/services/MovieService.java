package com.kinoarena.kinoarena.services;

import com.kinoarena.kinoarena.exceptions.NotFoundException;
import com.kinoarena.kinoarena.model.DTOs.age_restriction.AgeRestrictionForMovieDTO;
import com.kinoarena.kinoarena.model.DTOs.genre.GenreWithoutMoviesDTO;
import com.kinoarena.kinoarena.model.DTOs.movie.MovieInfoDTO;
import com.kinoarena.kinoarena.model.entities.Movie;
import com.kinoarena.kinoarena.model.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private MovieRepository movieRepository;
    private ModelMapper modelMapper;
    public MovieInfoDTO getMovieInfo(int mid) {
        Optional<Movie> movie = movieRepository.findById(mid);

        if(movie.isPresent()) {
            Movie m = movie.get();

            MovieInfoDTO dto = modelMapper.map(m, MovieInfoDTO.class);
            dto.setAgeRestriction(modelMapper.map(m.getAgeRestriction(), AgeRestrictionForMovieDTO.class));
            dto.setGenres(m.getMovieGenres()
                    .stream()
                    .map(g -> modelMapper.map(g, GenreWithoutMoviesDTO.class))
                    .collect(Collectors.toList()));

            return modelMapper.map(m, MovieInfoDTO.class);
        } else {
            throw new NotFoundException("Movie is not found!");
        }
    }
}
