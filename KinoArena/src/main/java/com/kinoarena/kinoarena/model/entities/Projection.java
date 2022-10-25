package com.kinoarena.kinoarena.model.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projections")
public class Projection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private LocalTime startTime;
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
    private Set<Seat> seats = new HashSet<>();
    @OneToMany(mappedBy = "projection")
    private Set<Ticket> tickets = new HashSet<>();
    @Column
    private double basePrice;
}
