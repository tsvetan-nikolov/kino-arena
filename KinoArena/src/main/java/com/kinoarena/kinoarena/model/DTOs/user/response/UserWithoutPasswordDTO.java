package com.kinoarena.kinoarena.model.DTOs.user.response;

import com.kinoarena.kinoarena.model.DTOs.movie.MovieSummarizedResponseDTO;
import com.kinoarena.kinoarena.model.entities.City;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserWithoutPasswordDTO {
    private int id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private City city;
    private boolean isAdmin;
    private List<MovieSummarizedResponseDTO> favouriteMovies;
}
