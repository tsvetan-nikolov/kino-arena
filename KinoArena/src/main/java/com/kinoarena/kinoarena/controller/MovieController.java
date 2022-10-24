package com.kinoarena.kinoarena.controller;

import com.kinoarena.kinoarena.model.DTOs.movie.MovieInfoDTO;
import com.kinoarena.kinoarena.model.repositories.MovieRepository;
import com.kinoarena.kinoarena.model.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController extends AbstractController{

    @Autowired
    private MovieService movieService;

    @GetMapping(value = "/movies/{mid}")
    public MovieInfoDTO getMovieInfo(@PathVariable int mid) {
        return movieService.getMovieInfo(mid);
    }
}
