package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<Hall, Integer> {

    Optional<Hall> findFirstByCinemaNameAndNumber(String cinemaName, Integer number);

    Optional<Hall> findFirstByNumber(Integer number);
}
