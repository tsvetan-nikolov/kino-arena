package com.kinoarena.kinoarena.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "projection_type_id")
    private ProjectionType projectionType;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;
    @OneToMany(mappedBy = "projection")
    private List<Seat> seats;
    @OneToMany(mappedBy = "projection")
    private List<Ticket> tickets;
    @Column
    private double basePrice;
}
