package com.kinoarena.kinoarena.controller;

import com.kinoarena.kinoarena.model.DTOs.tickets.TicketRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.tickets.TicketResponseDTO;
import com.kinoarena.kinoarena.model.entities.User;
import com.kinoarena.kinoarena.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController extends AbstractController {
    private final TicketService ticketService;

    @PostMapping("/booking/{projectionId}")
    public List<TicketResponseDTO> reserveTicket(
            @RequestParam TicketRequestDTO req, @AuthenticationPrincipal User user, @PathVariable int projectionId) {
        return ticketService.reserve(req, user, projectionId);
    }

}
