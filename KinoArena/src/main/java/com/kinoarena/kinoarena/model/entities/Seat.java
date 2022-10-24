package com.kinoarena.kinoarena.model.entities;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int number;
    @Column
    private int row;
    @Column
    private boolean isTaken;
    @ManyToOne
    @JoinColumn(name = "projection_id")
    private Projection projection;
    @OneToMany(mappedBy = "seat")
    private Set<Ticket> tickets = new HashSet<>();
}
