package com.kinoarena.kinoarena.model.DTOs.user;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserWithoutMoviesDTO {
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;

}

