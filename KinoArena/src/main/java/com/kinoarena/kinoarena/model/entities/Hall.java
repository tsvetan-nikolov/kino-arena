package com.kinoarena.kinoarena.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "halls")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @Column
//    private Cinema cinema;
}
