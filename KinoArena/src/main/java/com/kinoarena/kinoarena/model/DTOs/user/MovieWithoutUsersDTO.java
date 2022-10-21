package com.kinoarena.kinoarena.model.DTOs.user;

import com.kinoarena.kinoarena.model.entities.AgeRestriction;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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
