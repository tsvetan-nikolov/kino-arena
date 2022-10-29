package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

}
