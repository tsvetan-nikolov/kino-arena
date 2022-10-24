package com.kinoarena.kinoarena.model.DTOs.projection;

import com.kinoarena.kinoarena.model.DTOs.projection_type.ProjectionTypeInfoDTO;
import com.kinoarena.kinoarena.model.entities.ProjectionType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ProjectionInfoDTO {
    private int id;
    private LocalTime startTime;
    private LocalDate date;
    private ProjectionTypeInfoDTO projectionType;
}
