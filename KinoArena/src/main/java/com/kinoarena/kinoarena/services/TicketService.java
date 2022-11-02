package com.kinoarena.kinoarena.services;

import com.kinoarena.kinoarena.model.DTOs.projection.response.ProjectionResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.seat.SeatWithoutHallDTO;
import com.kinoarena.kinoarena.model.DTOs.tickets.ReserveTicketResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.tickets.TicketRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.tickets.TicketResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserReserveTicketDTO;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserWithoutPasswordDTO;
import com.kinoarena.kinoarena.model.entities.*;
import com.kinoarena.kinoarena.model.exceptions.NotFoundException;
import com.kinoarena.kinoarena.model.exceptions.UnauthorizedException;
import com.kinoarena.kinoarena.model.repositories.ProjectionRepository;
import com.kinoarena.kinoarena.model.repositories.SeatRepository;
import com.kinoarena.kinoarena.model.repositories.TicketRepository;
import com.kinoarena.kinoarena.model.repositories.TicketTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final ProjectionRepository projectionRepository;
    private final SeatRepository seatRepository;
    private final ModelMapper modelMapper;

//todo is there a ticket reserved
        @Transactional
        public ReserveTicketResponseDTO reserve (TicketRequestDTO req, User user,int projectionId){
            UserWithoutPasswordDTO userWithoutPasswordDTO = modelMapper.map(user, UserWithoutPasswordDTO.class);

            ProjectionResponseDTO projectionResponseDTO = modelMapper.map(
                    projectionRepository.findById(projectionId), ProjectionResponseDTO.class);

            List<TicketResponseDTO> ticketsToReserve = new ArrayList<>();

            reserveTickets(req, userWithoutPasswordDTO, projectionResponseDTO, ticketsToReserve, projectionId);
            double totalPrice = ticketsToReserve
                    .stream()
                    .mapToDouble(TicketResponseDTO::getTotalPrice)
                    .sum();

            return ReserveTicketResponseDTO
                    .builder()
                    .totalPrice(totalPrice)
                    .projection(projectionResponseDTO)
                    .tickets(ticketsToReserve)
                    .build();
        }

        private void reserveTickets (TicketRequestDTO req, UserWithoutPasswordDTO userWithoutPasswordDTO,
                                     ProjectionResponseDTO projectionResponseDTO,
                                     List < TicketResponseDTO > ticketsToReserve, int projectionId)
        {
            //TODO check if ticket exists
            Projection projection = projectionRepository.findById(projectionId)
                    .orElseThrow(() -> new NotFoundException("Projection not found!"));

            for (int i = 0; i < req.getTicketTypes().size(); i++) {
                Seat seat = seatRepository.findById(req.getSeatIds().get(i))
                        .orElseThrow(() -> new NotFoundException("Seat not found!"));

                if (!projection.getHall().getSeats().contains(seat)) {
                    throw new UnauthorizedException("Wrong seat for projection");
                }

                String ticketType = req.getTicketTypes().get(i);
                TicketType type = ticketTypeRepository.findFirstByType(ticketType)
                        .orElseThrow(() -> new NotFoundException("Ticket type " + ticketType + " not found!"));

                double projectionTypeAdditional = projectionResponseDTO.getProjectionType().getAdditionalCost();
                double ticketTypeAdditional = type.getAdditionalCost();
                double totalTicketPrice = projectionResponseDTO.getBasePrice() + ticketTypeAdditional + projectionTypeAdditional;

                TicketResponseDTO ticketToReserve =
                        createTicketResponse(modelMapper.map(seat, SeatWithoutHallDTO.class), type, totalTicketPrice);

                Ticket ticket = createTicket(userWithoutPasswordDTO, projectionResponseDTO, seat, type);

                Ticket ticketToSave = ticketRepository.save(ticket);
                ticketToReserve.setId(ticketToSave.getId());
                ticketsToReserve.add(ticketToReserve);

            }

        }

        private Ticket createTicket (UserWithoutPasswordDTO userWithoutPasswordDTO,
                ProjectionResponseDTO projectionResponseDTO, Seat seat, TicketType type){
            return Ticket
                    .builder()
                    .ticketType(type)
                    .projection(modelMapper.map(projectionResponseDTO, Projection.class))
                    .user(modelMapper.map(userWithoutPasswordDTO, User.class))
                    .seat(seat)
                    .build();
        }

        private static TicketResponseDTO createTicketResponse (SeatWithoutHallDTO seat, TicketType type,
        double totalPrice){
            return TicketResponseDTO
                    .builder()
                    .totalPrice(totalPrice)
                    .ticketType(type)
                    .seat(seat)
                    .build();
        }
    }
