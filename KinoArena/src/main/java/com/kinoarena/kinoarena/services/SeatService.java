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

@Service
@RequiredArgsConstructor
public class SeatService {

    private final ProjectionRepository projectionRepository;
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;

    public List<List<SeatForProjectionDTO>> showSeatsForProjection(int pid) {
        Projection p = projectionRepository.findById(pid).orElseThrow(() -> new NotFoundException("No projection found"));
        List<Seat> seats = p.getHall().getSeats();

        List<List<SeatForProjectionDTO>> seatsForProjection = new ArrayList<>();

        for (Seat seat : seats) {
            if (seatsForProjection.size() < seat.getRow()) {
                seatsForProjection.add(new ArrayList<>());
            }

            SeatForProjectionDTO seatDto = modelMapper.map(seat, SeatForProjectionDTO.class);

            Optional<Ticket> t = ticketRepository.findFirstBySeat(modelMapper.map(seat, Seat.class));

            if (t.isPresent()) {
                seatDto.setFree(false);
            }

            seatsForProjection.get(seat.getRow() - 1).add(seatDto);
        }

        return seatsForProjection;
    }
}
