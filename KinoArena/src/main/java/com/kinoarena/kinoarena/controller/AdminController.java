package com.kinoarena.kinoarena.controller;

import com.kinoarena.kinoarena.model.DTOs.cinema.CinemaDeleteRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.cinema.CinemaRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.hall.HallEditRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.hall.HallRequestDTO;
import com.kinoarena.kinoarena.model.entities.Cinema;
import com.kinoarena.kinoarena.model.entities.Hall;
import com.kinoarena.kinoarena.services.CinemaService;
import com.kinoarena.kinoarena.services.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(value = "/admin")
public class AdminController extends AbstractController {

    private final CinemaService cinemaService;

    private final HallService hallService;


    @PostMapping("/cinemas")
    public Cinema addCinema(@RequestBody CinemaRequestDTO cinema) {
        return cinemaService.add(cinema);
    }

    @PutMapping("/cinemas")
    public Cinema editCinema(@RequestBody CinemaRequestDTO cinema) {
        return cinemaService.edit(cinema);
    }

    @DeleteMapping("/cinemas")
    public String deleteCinema(@RequestBody CinemaDeleteRequestDTO cinema) {
        return cinemaService.delete(cinema);
    }

    @PostMapping("/halls")
    public Hall addHall(@RequestBody HallRequestDTO hall) {
        return hallService.add(hall);
    }

    @PutMapping("/halls")
    public Hall editHall(@RequestBody HallEditRequestDTO hall) {
        return hallService.edit(hall);
    }

    @DeleteMapping("/halls")
    public String deleteHall(@RequestBody HallRequestDTO hall) {
        return hallService.delete(hall);
    }
}
