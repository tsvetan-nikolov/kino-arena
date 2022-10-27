package com.kinoarena.kinoarena.model.DTOs.movie;

import java.time.LocalDate;

import com.kinoarena.kinoarena.model.entities.AgeRestriction;
import com.kinoarena.kinoarena.model.entities.Genre;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class MovieWithoutUsersDTO {
    private int id;
    private String name;
    private boolean isDubbed;
    private String description;
    private LocalDate premiere;
    private int duration;
    private String actors;
    private String director;
    private AgeRestriction ageRestriction;
    private Set<Genre> movieGenres;
}
