package com.kinoarena.kinoarena.model.DTOs.projection_type;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProjectionTypeInfoDTO { //todo not needed, because same as ProjectionType
    private final int id;
    private final String type;
}
