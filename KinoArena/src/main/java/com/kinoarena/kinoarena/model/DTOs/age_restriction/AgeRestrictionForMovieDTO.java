package com.kinoarena.kinoarena.model.DTOs.age_restriction;

import lombok.Data;

@Data
public class AgeRestrictionForMovieDTO {
    private String category;

    public AgeRestrictionForMovieDTO(String category) {
        this.category = category;
    }
}
