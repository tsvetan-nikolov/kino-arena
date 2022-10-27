package com.kinoarena.kinoarena.model.DTOs.movie;

import com.kinoarena.kinoarena.model.entities.AgeRestriction;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FavouriteMovieDTO {
    private int id;
    private String name;
    private AgeRestriction ageRestriction;
    //todo genre
}
