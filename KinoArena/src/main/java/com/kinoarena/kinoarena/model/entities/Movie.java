package com.kinoarena.kinoarena.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    private String description;
    @Column
    private LocalDate premiere;
    @Column
    private int duration;
    @Column
    private String actors;
    @Column
    private String director;
    @ManyToOne
    @JoinColumn(name = "age_restriction_id")
    private AgeRestriction ageRestriction;
    @OneToMany(mappedBy = "movie")
    private List<Projection> projections;
    @ManyToMany(mappedBy = "favouriteMovies")
    private List<User> users;
}
