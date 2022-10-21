package com.kinoarena.kinoarena.model.DTOs.movie;

import com.kinoarena.kinoarena.model.entities.AgeRestriction;
import com.kinoarena.kinoarena.model.entities.Projection;
import com.kinoarena.kinoarena.model.entities.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class MovieInfoDTO {
    private int id;
    private String name;
    private boolean isDubbed;
    private String description;
    private LocalDate premiere;
    private int duration;
    private String actors;
    private String director;
//    private AgeRestriction ageRestriction;
//    private List<Projection> projections;
//    private List<User> users;
}
