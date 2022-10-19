package com.kinoarena.kinoarena.model.entities;


import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.util.List;

@Data
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
    private List<Ticket> tickets;
}
