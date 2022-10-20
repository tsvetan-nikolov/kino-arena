package com.kinoarena.kinoarena.controller;

import com.kinoarena.kinoarena.model.entities.Projection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectionController {

    @GetMapping("/test")
    public int test() {
        return 5;
    }
}
