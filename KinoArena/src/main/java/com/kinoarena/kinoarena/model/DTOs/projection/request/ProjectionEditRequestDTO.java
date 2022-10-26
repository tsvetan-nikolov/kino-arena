package com.kinoarena.kinoarena.model.DTOs.projection.request;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ProjectionEditRequestDTO {
    private LocalTime startTime;
    private LocalTime newStartTime;
    private LocalDate date;
    private LocalDate newDate;
    private String projectionTypeName;
    private String movieName;
    private int hallNumber;
    private String cinemaName;
    private double basePrice;
    private double newBasePrice;
}
