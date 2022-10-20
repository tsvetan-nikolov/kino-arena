package com.kinoarena.kinoarena.model.DTOs.user.request;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
}
