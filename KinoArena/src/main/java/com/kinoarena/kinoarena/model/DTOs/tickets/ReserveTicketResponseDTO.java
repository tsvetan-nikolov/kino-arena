package com.kinoarena.kinoarena.model.DTOs.tickets;

import com.kinoarena.kinoarena.model.DTOs.projection.response.ProjectionResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserWithoutPasswordDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReserveTicketResponseDTO {
    private double totalPrice;
    private ProjectionResponseDTO projection;
//    private UserWithoutPasswordDTO user;
    private List<TicketResponseDTO> tickets;
}
