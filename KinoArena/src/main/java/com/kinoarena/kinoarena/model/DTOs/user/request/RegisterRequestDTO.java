package com.kinoarena.kinoarena.model.DTOs.user.request;

import com.kinoarena.kinoarena.model.entities.Cinema;
import com.kinoarena.kinoarena.model.entities.City;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
public class RegisterRequestDTO {

//    TODO: check if @NotNull should be used or not
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
    private LocalDate dateOfBirth;

//    @NotEmpty
//    @ManyToOne
//    private City city;

//    private Cinema cinema;
}