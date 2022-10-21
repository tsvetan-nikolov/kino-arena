package com.kinoarena.kinoarena.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String genre;
    @ManyToMany(mappedBy = "movieGenres")
    private List<Movie> movies;
}
