package com.kinoarena.kinoarena.model.dto.user.response;

import com.kinoarena.kinoarena.model.entities.City;

import java.time.LocalDate;

public abstract class UserResponse {
//TODO maybe implement somewhere...
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private City city;

}
