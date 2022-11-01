package com.kinoarena.kinoarena.model.DTOs.tickets;

import com.kinoarena.kinoarena.model.DTOs.projection.response.ProjectionResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserWithoutPasswordDTO;
import com.kinoarena.kinoarena.model.entities.Seat;
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
    private ProjectionResponseDTO projection;
    private UserWithoutPasswordDTO user;
    private Seat seat;
}
