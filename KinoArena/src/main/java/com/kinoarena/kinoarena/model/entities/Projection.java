package com.kinoarena.kinoarena.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "projections")
public class Projection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private LocalTime start_time;
    @Column
    private LocalDate date;
//    @Column
//    private ProjectionType projectionType;
//    @Column
//    private Movie movie;
//    @Column
//    private Hall hall;
    @Column
    private double basePrice;
}
