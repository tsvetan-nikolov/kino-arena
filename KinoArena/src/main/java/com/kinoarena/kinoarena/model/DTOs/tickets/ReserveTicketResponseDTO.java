package com.kinoarena.kinoarena.model.DTOs.tickets;

import com.kinoarena.kinoarena.model.DTOs.projection.response.ProjectionResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReserveTicketResponseDTO {
    private double totalPrice;
    private ProjectionResponseDTO projection;
    private List<TicketResponseDTO> tickets;
}
