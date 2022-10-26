package com.kinoarena.kinoarena.model.DTOs.projection.response;

import com.kinoarena.kinoarena.model.DTOs.movie.MovieWithoutUsersDTO;
import com.kinoarena.kinoarena.model.entities.Hall;
import com.kinoarena.kinoarena.model.entities.ProjectionType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectionResponseDTO { /*TODO unify responses?*/
    private int id;
    private LocalTime startTime;
    private LocalDate date;
    private ProjectionType projectionType;
    private MovieWithoutUsersDTO movie;
    private Hall hall;
    private double basePrice; //todo check prices if all are double
}
