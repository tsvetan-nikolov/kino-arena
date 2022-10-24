package com.kinoarena.kinoarena.controller;

import com.kinoarena.kinoarena.model.DTOs.movie.MovieInfoDTO;
import com.kinoarena.kinoarena.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovieController extends AbstractController{

    private final MovieService movieService;

    @GetMapping(value = "/movies/{mid}")
    public MovieInfoDTO getMovieInfo(@PathVariable int mid) {
        return movieService.getMovieInfo(mid);
    }
}
