package com.kinoarena.kinoarena.controller;

import com.kinoarena.kinoarena.model.DTOs.cinema.CinemaInfoDTO;
import com.kinoarena.kinoarena.model.DTOs.movie.MovieProgramDTO;
import com.kinoarena.kinoarena.model.entities.Cinema;
import com.kinoarena.kinoarena.model.entities.Movie;
import com.kinoarena.kinoarena.model.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CinemaController extends AbstractController{
    @Autowired
    private CinemaService cinemaService;

    @GetMapping(value = "/cinemas")
    public List<CinemaInfoDTO> getAllCinemas() {
        return cinemaService.getAllCinemas();
    }

    @GetMapping(value = "/cinemas/{cid}/program")
    public Map<String, MovieProgramDTO> getProgram(@PathVariable int cid) {
        return cinemaService.getProgram(cid);
    }
}
