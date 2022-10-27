package com.kinoarena.kinoarena.model.repositories;

import com.kinoarena.kinoarena.model.entities.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketTypeRepository extends JpaRepository<TicketType, Integer> {

    Optional<TicketType> findFirstByType(String ticketType);
}
