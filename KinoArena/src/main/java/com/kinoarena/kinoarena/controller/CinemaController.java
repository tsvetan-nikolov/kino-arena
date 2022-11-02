package com.kinoarena.kinoarena.controller;

import com.kinoarena.kinoarena.model.DTOs.cinema.CinemaInfoResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.projection.response.ProjectionInfoDTO;
import com.kinoarena.kinoarena.services.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CinemaController extends AbstractController {

    private final CinemaService cinemaService;

    @GetMapping(value = "/cinemas")
    public List<CinemaInfoResponseDTO> getAllCinemas() {
        return cinemaService.getAllCinemas();
    }

    @GetMapping(value = "/cinemas/{cid}/program")
    public List<ProjectionInfoDTO> getProgram(@PathVariable int cid) {
        return cinemaService.getProgram(cid);
    }

    @GetMapping(value = "/cinemas/{cityName}")
    public List<CinemaInfoResponseDTO> filterCinemasByCity(@PathVariable String cityName) {
        return cinemaService.filterCinemasByCity(cityName);
    }
}
