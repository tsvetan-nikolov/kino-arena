package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.Movie;
import com.kinoarena.kinoarena.model.entities.Projection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ProjectionRepository extends JpaRepository<Projection, Integer> {

    List<Projection> findAllByMovieId(Integer movieId);
}
