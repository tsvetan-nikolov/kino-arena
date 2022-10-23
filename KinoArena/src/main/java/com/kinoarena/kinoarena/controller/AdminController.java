package com.kinoarena.kinoarena.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import static com.kinoarena.kinoarena.util.Constants.ROLE_ADMIN;


@RestController
public class AdminController {

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/admin")
    public String addCinema() {
        return "Method entered!";
    }
}
