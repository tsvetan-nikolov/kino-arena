package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findAllByNameIn(List<String> names);
}