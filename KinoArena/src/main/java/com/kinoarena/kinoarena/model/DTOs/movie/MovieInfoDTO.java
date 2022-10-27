package com.kinoarena.kinoarena.model.DTOs.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.kinoarena.kinoarena.model.DTOs.genre.GenreWithoutMoviesDTO;
import com.kinoarena.kinoarena.model.DTOs.projection.ProjectionWithCinemaDTO;
import com.kinoarena.kinoarena.model.entities.AgeRestriction;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

@Data
public class MovieInfoDTO {
    private int id;
    private String name;
    private String description;
    private AgeRestriction ageRestriction;
    private boolean isDubbed;
    private int duration;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate premiere;
    private String director;
    private List<GenreWithoutMoviesDTO> genres;
    private String actors;
    private List<ProjectionWithCinemaDTO> projections;
}
