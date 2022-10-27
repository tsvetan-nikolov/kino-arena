package com.kinoarena.kinoarena.model.DTOs.tickets;

import lombok.Getter;

@Getter
public class TicketRequestDTO {
    private String ticketType;
    private int projectionId;
    private int seatId; //todo @GetMapping() for id;
}
