package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.Seat;
import com.kinoarena.kinoarena.model.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Optional<Ticket> findFirstBySeat(Seat seat);
}
