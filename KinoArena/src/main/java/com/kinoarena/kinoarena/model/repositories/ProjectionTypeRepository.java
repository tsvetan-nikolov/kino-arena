package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.ProjectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectionTypeRepository extends JpaRepository<ProjectionType, Integer> {

    Optional<ProjectionType> findById(Integer integer);

    Optional<ProjectionType> findFirstByType(String type);
}
