package com.kinoarena.kinoarena.model.DTOs.projection.response;

import com.kinoarena.kinoarena.model.DTOs.projection_type.ProjectionTypeInfoDTO;
import com.kinoarena.kinoarena.model.entities.Movie;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter //todo replace all such with getters/setters???
public class ProjectionInfoDTO {
    private int id;
    private LocalTime startTime;
    private LocalDate date;
    private ProjectionTypeInfoDTO projectionType;
    private Movie movie;
}
