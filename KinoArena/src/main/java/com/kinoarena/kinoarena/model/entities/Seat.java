package com.kinoarena.kinoarena.model.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;

}
