package com.kinoarena.kinoarena.model.DTOs.movie;

import com.kinoarena.kinoarena.model.DTOs.age_restriction.AgeRestrictionForMovieDTO;
import lombok.Data;

@Data
public class FavouriteMovieDTO {
    private int id;
    private String name;
    private AgeRestrictionForMovieDTO ageRestriction;
    //todo genre
}
