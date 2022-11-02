package com.kinoarena.kinoarena.model.DTOs.projection.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ProjectionEditRequestDTO {
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime startTime;
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime newStartTime;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate date;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate newDate;
    private String projectionTypeName;
    private String movieName;
    private int hallNumber;
    private String cinemaName;
    private double basePrice;
    private double newBasePrice;
}
