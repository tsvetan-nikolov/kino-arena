package com.kinoarena.kinoarena.model.DTOs.movie;

import com.kinoarena.kinoarena.model.DTOs.genre.GenreWithoutMoviesDTO;
import com.kinoarena.kinoarena.model.entities.AgeRestriction;
import lombok.Data;

import java.util.List;

@Data
public class MovieSummarizedResponseDTO {
    private int id;
    private String name;
    private AgeRestriction ageRestriction;
    private List<GenreWithoutMoviesDTO> genres;
    private boolean isDubbed;
}
