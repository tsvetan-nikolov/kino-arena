package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.ProjectionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectionTypeRepository extends JpaRepository<ProjectionType, Integer> {
    Optional<ProjectionType> findById(Integer integer);
}
