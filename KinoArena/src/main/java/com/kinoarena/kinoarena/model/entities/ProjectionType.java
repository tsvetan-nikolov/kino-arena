package com.kinoarena.kinoarena.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany(mappedBy = "projectionType")
    private List<Projection> projections;
}
