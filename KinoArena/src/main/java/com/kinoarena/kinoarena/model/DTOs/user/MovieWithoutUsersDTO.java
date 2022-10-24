package com.kinoarena.kinoarena.model.DTOs.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieWithoutUsersDTO {
    private String name;
    private boolean isDubbed;
    private String description;
    private LocalDate premiere;
    private int duration;
    private String actors;
    private String director;
    //todo genre
}
