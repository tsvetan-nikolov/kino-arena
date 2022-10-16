package com.kinoarena.kinoarena.model.entities;


import lombok.Data;
import lombok.Generated;

import javax.persistence.*;

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
//    @Column
//    private Projection projection;
}
