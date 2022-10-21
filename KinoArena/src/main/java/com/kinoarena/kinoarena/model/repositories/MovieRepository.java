package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.DTOs.movie.MovieInfoDTO;
import com.kinoarena.kinoarena.model.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieInfoDTO, Integer> {

    @Override
    Optional<MovieInfoDTO> findById(Integer integer);
}
