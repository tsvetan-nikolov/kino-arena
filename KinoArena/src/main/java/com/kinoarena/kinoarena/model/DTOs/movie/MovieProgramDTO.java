package com.kinoarena.kinoarena.model.DTOs.movie;

import com.kinoarena.kinoarena.model.DTOs.age_restriction.AgeRestrictionForMovieDTO;
import com.kinoarena.kinoarena.model.DTOs.projection.response.ProjectionInfoDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieProgramDTO {
    private int id;
    private String name;
    private LocalDate premiere;
    private AgeRestrictionForMovieDTO ageRestriction;
    private boolean isDubbed;
    private List<ProjectionInfoDTO> projections;
}
