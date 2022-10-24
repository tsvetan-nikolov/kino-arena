package com.kinoarena.kinoarena.model.DTOs.hall;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HallEditRequestDTO {
    private int oldNumber;
    private int newNumber;
    private String cinemaName;
}
