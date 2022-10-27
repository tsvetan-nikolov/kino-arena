package com.kinoarena.kinoarena.controller;

import com.kinoarena.kinoarena.model.DTOs.tickets.TicketRequestDTO;
import com.kinoarena.kinoarena.model.entities.Ticket;
import com.kinoarena.kinoarena.services.TicketService;
import com.kinoarena.kinoarena.util.annotation.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController extends AbstractController {
    private final TicketService ticketService;

    @PostMapping("/reserve/{ticketId}")
    public Ticket reserveTicket(@RequestParam TicketRequestDTO req, @UserId int userId) {
        return ticketService.reserve(req, userId);
    }

}
