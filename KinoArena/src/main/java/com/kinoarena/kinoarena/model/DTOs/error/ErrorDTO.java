package com.kinoarena.kinoarena.model.DTOs.error;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDTO {
    private String msg;
    private int status;
    private LocalDateTime time;
}
