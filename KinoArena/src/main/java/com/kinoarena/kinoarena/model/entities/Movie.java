package com.kinoarena.kinoarena.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private boolean isDubbed;
    @Column
    private String ageRestriction;
    @Column
    private String description;
    @Column
    private LocalDate premiere;
    @Column
    private int duration;
    @Column
    private String actors;
    @Column
    private String director;
}
