package com.kinoarena.kinoarena.model.DTOs.tickets;

import com.kinoarena.kinoarena.model.DTOs.seat.SeatWithoutHallDTO;
import com.kinoarena.kinoarena.model.entities.TicketType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TicketResponseDTO {
    private int id;
    private TicketType ticketType;
    private SeatWithoutHallDTO seat;
    private double totalPrice;
}
