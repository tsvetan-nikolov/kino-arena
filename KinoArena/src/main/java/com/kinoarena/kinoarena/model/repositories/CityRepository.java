package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findCityById(int id);

    Optional<City> findFirstByName(String name);
}
