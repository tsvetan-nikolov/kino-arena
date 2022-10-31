package com.kinoarena.kinoarena.model.DTOs.tickets;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class TicketRequestDTO {
    private ArrayList<String> ticketTypes;
    private ArrayList<Integer> seatIds;
}
