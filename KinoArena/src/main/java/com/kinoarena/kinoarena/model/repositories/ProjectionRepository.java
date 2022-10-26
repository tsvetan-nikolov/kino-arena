package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.Hall;
import com.kinoarena.kinoarena.model.entities.Movie;
import com.kinoarena.kinoarena.model.entities.Projection;
import com.kinoarena.kinoarena.model.entities.ProjectionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ProjectionRepository extends JpaRepository<Projection, Integer> {

    Optional<Projection> findFirstByDateAndStartTimeAndHall(LocalDate date, LocalTime time, Hall hall);

    Optional<Projection> findFirstByProjectionTypeAndMovieAndHallAndStartTimeAndDateAndBasePrice
            (ProjectionType type, Movie movie, Hall hall, LocalTime start, LocalDate date, double price);

    List<Projection> findAllByHallAndDate(Hall hall, LocalDate date);

    List<Projection> findAllByMovieId(Integer movieId);
}
