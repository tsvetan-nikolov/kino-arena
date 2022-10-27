package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {


}
