package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {

    List<Cinema> findAll();

    Optional<Cinema> findFirstByName(String name);

    Optional<Cinema> findFirstByAddress(String address);

    Optional<Cinema> findFirstByNameOrAddress(String name, String address);

    Optional<Cinema> findFirstByCityName(String cityName);
}
