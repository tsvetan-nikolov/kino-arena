package com.kinoarena.kinoarena.model.DTOs.projection.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.kinoarena.kinoarena.model.DTOs.hall.HallWithoutSeatsDTO;
import com.kinoarena.kinoarena.model.DTOs.movie.MovieInfoDTO;
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

    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime startTime;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate date;

    private ProjectionType projectionType;
    private MovieInfoDTO movie;
    private HallWithoutSeatsDTO hall;
    private double basePrice; //todo check prices if all are double
}
