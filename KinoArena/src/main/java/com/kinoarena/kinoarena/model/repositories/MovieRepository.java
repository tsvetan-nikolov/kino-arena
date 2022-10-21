package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.DTOs.movie.MovieInfoDTO;
import com.kinoarena.kinoarena.model.entities.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Override
    Optional<Movie> findById(Integer integer);
}
