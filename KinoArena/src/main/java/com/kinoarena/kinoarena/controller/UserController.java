package com.kinoarena.kinoarena.controller;

import com.kinoarena.kinoarena.model.dto.user.request.RegisterRequestDTO;
import com.kinoarena.kinoarena.model.dto.user.response.UserInfoResponse;
import com.kinoarena.kinoarena.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserInfoResponse register(@RequestBody @Valid @NotNull RegisterRequestDTO user) {
        return userService.register(user);
    }

}
