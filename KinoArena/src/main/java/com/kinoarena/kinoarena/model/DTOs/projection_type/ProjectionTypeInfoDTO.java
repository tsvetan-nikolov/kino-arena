package com.kinoarena.kinoarena.model.DTOs.projection_type;

import lombok.Data;

@Data
public class ProjectionTypeInfoDTO {
    private int id;
    private String type;

    public ProjectionTypeInfoDTO(int id, String type) {
        this.id = id;
        this.type = type;
    }
}
