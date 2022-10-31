package com.kinoarena.kinoarena.model.DTOs.seat;

import lombok.Data;

@Data
public class SeatForProjectionDTO {
    private int id;
    private int number;
    private int row;
    private boolean isFree = true;
}
