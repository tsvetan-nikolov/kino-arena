package com.kinoarena.kinoarena.model.DTOs.projection.request;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ProjectionRequestDTO {
    private LocalTime startTime;
    private LocalDate date;
    private String projectionTypeName;
    private String movieName;
    private int hallNumber;
    private String cinemaName;
    private double basePrice;
}

