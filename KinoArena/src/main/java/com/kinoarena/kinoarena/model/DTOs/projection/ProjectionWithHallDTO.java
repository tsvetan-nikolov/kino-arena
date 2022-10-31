package com.kinoarena.kinoarena.model.DTOs.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.kinoarena.kinoarena.model.DTOs.cinema.CinemaInfoDTO;
import com.kinoarena.kinoarena.model.DTOs.projection_type.ProjectionTypeInfoDTO;
import com.kinoarena.kinoarena.model.entities.Hall;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ProjectionWithHallDTO {
    private int id;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime startTime;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate date;
    private ProjectionTypeInfoDTO projectionType;
    private Hall hall;
    private CinemaInfoDTO cinema;
}
