package com.kinoarena.kinoarena.model.DTOs.user.request;

import lombok.Data;

@Data
public class ChangePasswordRequestDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
