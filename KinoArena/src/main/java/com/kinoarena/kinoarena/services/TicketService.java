package com.kinoarena.kinoarena.services;

import com.kinoarena.kinoarena.model.DTOs.tickets.TicketRequestDTO;
import com.kinoarena.kinoarena.model.entities.*;
import com.kinoarena.kinoarena.model.exceptions.NotFoundException;
import com.kinoarena.kinoarena.model.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final ProjectionRepository projectionRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;


    public Ticket reserve(TicketRequestDTO req, int userId) {
        TicketType type = ticketTypeRepository.findFirstByType(req.getTicketType())
                .orElseThrow(() -> new NotFoundException("Ticket type " + req.getTicketType() + " not found!"));
        Projection projection = projectionRepository.findById(req.getProjectionId())
                .orElseThrow(() -> new NotFoundException("Projection doesn't exist!"));
        Seat seat = seatRepository.findById(req.getSeatId())
                .orElseThrow(() -> new NotFoundException("Seat doesn't exist!"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User doesn't exist!"));


        //TODO check if taken
        Ticket ticket = Ticket
                .builder()
                .ticketType(type)
                .projection(projection)
                .seat(seat)
                .user(user)
                .build();

        ticket = ticketRepository.save(ticket);
        return ticket;
    }
}
