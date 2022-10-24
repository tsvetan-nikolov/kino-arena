package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HallRepository extends JpaRepository<Hall, Integer> {


    Hall findFirstByCinemaNameAndNumber(String cinemaName, Integer number);

    Optional<Hall> findFirstByNumber(Integer number);
}
