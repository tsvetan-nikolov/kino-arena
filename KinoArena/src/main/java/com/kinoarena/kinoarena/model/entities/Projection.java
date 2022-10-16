package com.kinoarena.kinoarena.model.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Projection {
    private int id;
    private LocalTime start_time;
    private LocalDate date;
    private ProjectionType projectionType;
    private Movie movie;
    private Hall hall;
    private double basePrice;
}
