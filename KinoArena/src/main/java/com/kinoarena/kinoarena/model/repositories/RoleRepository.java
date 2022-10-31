package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findAllByNameIn(List<String> names);
}