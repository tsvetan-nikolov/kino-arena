package com.kinoarena.kinoarena.model.DTOs.user.request;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
