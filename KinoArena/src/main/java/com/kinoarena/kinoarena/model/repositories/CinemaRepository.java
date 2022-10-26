package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CinemaRepository extends JpaRepository<Cinema, Integer> {


    Optional<Cinema> findFirstByName(String name);

    Optional<Cinema> findFirstByAddress(String address);

    Optional<Cinema> findFirstByNameOrAddress(String name, String address);

    Optional<Cinema> findFirstByCityName(String cityName);
}
