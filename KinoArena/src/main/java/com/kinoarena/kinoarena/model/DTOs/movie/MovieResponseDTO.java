package com.kinoarena.kinoarena.model.DTOs.movie;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieResponseDTO {
    private boolean removed;
    private String message;

}
