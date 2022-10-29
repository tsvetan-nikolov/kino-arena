package com.kinoarena.kinoarena.services;

import com.kinoarena.kinoarena.model.DTOs.seat.SeatForProjectionDTO;
import com.kinoarena.kinoarena.model.entities.Projection;
import com.kinoarena.kinoarena.model.entities.Seat;
import com.kinoarena.kinoarena.model.entities.Ticket;
import com.kinoarena.kinoarena.model.exceptions.NotFoundException;
import com.kinoarena.kinoarena.model.repositories.ProjectionRepository;
import com.kinoarena.kinoarena.model.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final ProjectionRepository projectionRepository;
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;
    public List<List<SeatForProjectionDTO>> showSeatsForProjection(int pid) {
        Projection p = projectionRepository.findById(pid).orElseThrow(() -> new NotFoundException("No projection found"));
        List<Seat> seats = p.getHall().getSeats();
        List<SeatForProjectionDTO> dto = seats.stream().map(seat -> modelMapper.map(seat, SeatForProjectionDTO.class)).collect(Collectors.toList());

        List<List<SeatForProjectionDTO>> seatsForProjection = new ArrayList<>();

        for (SeatForProjectionDTO seat : dto
             ) {
            if(seatsForProjection.size() < seat.getRow()) {
                seatsForProjection.add(new ArrayList<>());
            }

            Optional<Ticket> t = ticketRepository.findFirstBySeat(modelMapper.map(seat, Seat.class));

            if(t.isPresent()) {
                seat.setFree(false);
            }

            seatsForProjection.get(seat.getRow() - 1).add(seat);
        }

        return seatsForProjection;
    }
}
