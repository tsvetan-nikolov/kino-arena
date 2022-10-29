package com.kinoarena.kinoarena.model.DTOs.seat;

import lombok.Data;

import javax.persistence.Column;

@Data
public class SeatForProjectionDTO {
    private int id;
    private int number;
    private int row;
    private boolean isFree = true;
}
