package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
    Optional<Gender> findFirstByGender(String gender);
}
