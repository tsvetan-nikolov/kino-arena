package com.kinoarena.kinoarena.services;

import com.kinoarena.kinoarena.model.exceptions.BadRequestException;
import com.kinoarena.kinoarena.model.exceptions.NotFoundException;
import com.kinoarena.kinoarena.model.DTOs.hall.HallEditRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.hall.HallRequestDTO;
import com.kinoarena.kinoarena.model.entities.Cinema;
import com.kinoarena.kinoarena.model.entities.Hall;
import com.kinoarena.kinoarena.model.repositories.CinemaRepository;
import com.kinoarena.kinoarena.model.repositories.HallRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class HallService {
    private final ModelMapper modelMapper;
    private final HallRepository hallRepository;

    private final CinemaRepository cinemaRepository;

    public Hall add(HallRequestDTO h) {
        Cinema cinema = cinemaRepository.findFirstByName(h.getCinemaName());
        Hall hallChecker = hallRepository.findFirstByCinemaNameAndNumber(h.getCinemaName(), h.getNumber());

        if (cinema == null) throw new NotFoundException("Cinema doesn't exist!");
        if (hallChecker != null) throw new BadRequestException("Hall already exists");

        Hall hall = Hall
                .builder()
                .cinema(cinema)
                .number(h.getNumber())
                .build();

        hallRepository.save(hall);
        return hall;
    }

    @Transactional
    public Hall edit(HallEditRequestDTO h) {
        Cinema cinema = cinemaRepository.findFirstByName(h.getCinemaName());
        Hall hall = hallRepository.findFirstByCinemaNameAndNumber(h.getCinemaName(), h.getOldNumber());

        if (cinema == null) throw new NotFoundException("Cinema doesn't exist!");
        if (hall == null) throw new BadRequestException("Hall doesn't exists");

        hall.setNumber(h.getNewNumber());
        hall.setCinema(cinema);

        return hall;
    }

    public String delete(HallRequestDTO h) {
        Hall hall = hallRepository.findFirstByCinemaNameAndNumber(h.getCinemaName(), h.getNumber());
        boolean hallIsPresent = hall != null;
        if (hallIsPresent) hallRepository.delete(hall);
        return hallIsPresent ? "Hall deleted!" : "No hall with number \"" + h.getNumber() + "\" found!";
    }
}
