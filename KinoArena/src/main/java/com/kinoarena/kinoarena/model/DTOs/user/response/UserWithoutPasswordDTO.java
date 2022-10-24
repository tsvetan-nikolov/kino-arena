package com.kinoarena.kinoarena.model.DTOs.user.response;

import com.kinoarena.kinoarena.model.DTOs.user.MovieWithoutUsersDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserWithoutPasswordDTO {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private boolean isAdmin;
    private List<MovieWithoutUsersDTO> favouriteMovies;
}
