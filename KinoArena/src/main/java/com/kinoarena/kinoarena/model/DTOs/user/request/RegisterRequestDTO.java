package com.kinoarena.kinoarena.model.DTOs.user.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
public class RegisterRequestDTO {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String middleName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    @NotEmpty
    private String cityName;

    @NotEmpty
    private LocalDate dateOfBirth;

    @NotEmpty
    private String gender;

//    private Cinema cinema;
}
