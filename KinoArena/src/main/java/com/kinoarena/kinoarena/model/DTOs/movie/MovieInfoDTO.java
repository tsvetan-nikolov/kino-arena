package com.kinoarena.kinoarena.model.DTOs.movie;

import com.kinoarena.kinoarena.model.DTOs.age_restriction.AgeRestrictionForMovieDTO;
import com.kinoarena.kinoarena.model.DTOs.genre.GenreWithoutMoviesDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieInfoDTO {
    private int id;
    private String name;
    private boolean isDubbed;
    private String description;
    private LocalDate premiere;
    private int duration;
    private String actors;
    private String director;
    private AgeRestrictionForMovieDTO ageRestriction;
    private List<GenreWithoutMoviesDTO> genres;
}
