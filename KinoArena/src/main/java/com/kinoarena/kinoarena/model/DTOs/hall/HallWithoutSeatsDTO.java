package com.kinoarena.kinoarena.model.DTOs.hall;

import com.kinoarena.kinoarena.model.entities.Cinema;
import lombok.Data;

@Data
public class HallWithoutSeatsDTO {
    private int id;
    private Cinema cinema;
    private int number;
}
