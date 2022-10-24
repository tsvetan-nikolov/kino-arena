package com.kinoarena.kinoarena.model.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
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
//    @OneToMany(mappedBy = "movie")
//    private Set<Projection> projections = new HashSet<>();
    @ManyToMany(mappedBy = "favouriteMovies", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "movies_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_genre_id"))
    private Set<Genre> movieGenres = new HashSet<>();
}
