package com.kinoarena.kinoarena.model.DTOs.user;
import lombok.Data;

import java.time.LocalDate;

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
}
