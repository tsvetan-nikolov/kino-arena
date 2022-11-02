package com.kinoarena.kinoarena.model.DTOs.user.response;

import lombok.Data;

@Data
public class UserReserveTicketDTO {
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
}
