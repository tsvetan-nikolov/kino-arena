package com.kinoarena.kinoarena.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectionController extends AbstractController {

    @GetMapping("/test")
    public int test() {
        return 5;
    } //TODO
}
