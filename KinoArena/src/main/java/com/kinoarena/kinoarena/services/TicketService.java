package com.kinoarena.kinoarena.services;

import com.kinoarena.kinoarena.model.DTOs.projection.response.ProjectionResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.tickets.TicketRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.tickets.TicketResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserWithoutPasswordDTO;
import com.kinoarena.kinoarena.model.entities.Seat;
import com.kinoarena.kinoarena.model.entities.Ticket;
import com.kinoarena.kinoarena.model.entities.TicketType;
import com.kinoarena.kinoarena.model.entities.User;
import com.kinoarena.kinoarena.model.exceptions.NotFoundException;
import com.kinoarena.kinoarena.model.repositories.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final ProjectionRepository projectionRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public List<TicketResponseDTO> reserve(TicketRequestDTO req, User user, int projectionId) {
        UserWithoutPasswordDTO userWithoutPasswordDTO = modelMapper.map(user, UserWithoutPasswordDTO.class);
        ProjectionResponseDTO projectionResponseDTO = modelMapper.map(
                projectionRepository.findById(projectionId), ProjectionResponseDTO.class);

        List<TicketResponseDTO> ticketsToReserve = new ArrayList<>();
        //TODO check if ticket exists
        for (int i = 0; i < req.getTicketTypes().size(); i++) {
            Seat seat = seatRepository.findById(req.getSeatIds().get(i))
                    .orElseThrow(() -> new NotFoundException("Seat not found!"));

            String ticketType = req.getTicketTypes().get(i);
            TicketType type = ticketTypeRepository.findFirstByType(ticketType)
                    .orElseThrow(() -> new NotFoundException("Ticket type " + ticketType + " not found!"));

            TicketResponseDTO ticketToReserve =
                    TicketResponseDTO
                            .builder()
                            .ticketType(type)
                            .projection(projectionResponseDTO)
                            .user(userWithoutPasswordDTO)
                            .seat(seat)
                            .build();

            Ticket ticketToSave = ticketRepository.save(modelMapper.map(ticketToReserve, Ticket.class));
            ticketToReserve.setId(ticketToSave.getId());
            ticketsToReserve.add(ticketToReserve);
        }
        return ticketsToReserve;
    }
}
