package com.kinoarena.kinoarena.services;

import com.kinoarena.kinoarena.model.DTOs.cinema.CinemaDeleteRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.cinema.CinemaRequestDTO;
import com.kinoarena.kinoarena.model.entities.Cinema;
import com.kinoarena.kinoarena.model.entities.City;
import com.kinoarena.kinoarena.model.repositories.CinemaRepository;
import com.kinoarena.kinoarena.model.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CinemaService {
    private final CityRepository cityRepository;
    private final CinemaRepository cinemaRepository;

    public Cinema add(CinemaRequestDTO c) {
        City city = cityRepository.findFirstByName(c.getCityName())
                .orElse(cityRepository.save(new City(c.getCityName())));

        Cinema cinema = Cinema.builder()
                .name(c.getName())
                .address(c.getAddress())
                .city(city)
                .build();

        cinemaRepository.save(cinema);
        return cinema;
    }

    @Transactional
    public Cinema edit(CinemaRequestDTO c) {
        City city = cityRepository.findFirstByName(c.getCityName())
                .orElse(cityRepository.save(new City(c.getCityName())));

        Cinema cinema = cinemaRepository.findFirstByNameOrAddress(c.getName(), c.getAddress());

        if (cinema != null) {
            cinema.setName(c.getName());
            cinema.setAddress(c.getAddress());
            cinema.setCity(city);
        }

        return cinema;
    }

    public String delete(CinemaDeleteRequestDTO c) {
        Cinema cinema = cinemaRepository.findFirstByName(c.getName());
        boolean cinemaIsPresent = cinema != null;
        if (cinemaIsPresent) cinemaRepository.delete(cinema);
        return cinemaIsPresent ? "Cinema deleted!" : "No cinema with name \"" + c.getName() + "\" found!";
    }
}
