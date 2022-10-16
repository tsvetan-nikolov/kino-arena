package com.kinoarena.kinoarena.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "projection_types")
public class ProjectionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String type;
    @Column
    private double additionalPrice;
}
