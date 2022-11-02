package com.kinoarena.kinoarena.model.DTOs.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.kinoarena.kinoarena.model.DTOs.cinema.CinemaInfoResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.projection_type.ProjectionTypeResponseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ProjectionWithoutHallDTO {
    private int id;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime startTime;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate date;
    private ProjectionTypeResponseDTO projectionType;
    private CinemaInfoResponseDTO cinema;
}
