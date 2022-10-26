package com.kinoarena.kinoarena.services;

import com.kinoarena.kinoarena.model.DAOs.CinemaDAO;
import com.kinoarena.kinoarena.model.DTOs.cinema.CinemaDeleteRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.cinema.CinemaInfoDTO;
import com.kinoarena.kinoarena.model.DTOs.cinema.CinemaRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.city.CityInfoDTO;
import com.kinoarena.kinoarena.model.DTOs.movie.MovieProgramDTO;
import com.kinoarena.kinoarena.model.entities.Cinema;
import com.kinoarena.kinoarena.model.entities.City;
import com.kinoarena.kinoarena.model.exceptions.BadRequestException;
import com.kinoarena.kinoarena.model.repositories.CinemaRepository;
import com.kinoarena.kinoarena.model.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CinemaService {
    private final CityRepository cityRepository;
    private final CinemaRepository cinemaRepository;

    private final ModelMapper modelMapper;
    private final CinemaDAO cinemaDAO;

    public Cinema add(CinemaRequestDTO c) {
        if (cinemaRepository.findFirstByAddress(c.getAddress()).isPresent()
                || cinemaRepository.findFirstByName(c.getName()).isPresent()) {//todo should i check by the name too?
            throw new BadRequestException("Cinema already exists");
        }

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

        Cinema cinema = cinemaRepository.findFirstByNameOrAddress(c.getName(), c.getAddress())
                .orElseThrow(() -> new BadRequestException("Cinema doesn't exist!"));

        cinema.setName(c.getName());
        cinema.setAddress(c.getAddress());
        cinema.setCity(city);
        return cinema;
    }

    public String delete(CinemaDeleteRequestDTO c) {
        Cinema cinema = cinemaRepository.findFirstByName(c.getName())
                .orElseThrow(() -> new BadRequestException("No cinema with name '" + c.getName() + "' found!"));
        cinemaRepository.delete(cinema);
        return "Cinema deleted!";
    }


    public List<CinemaInfoDTO> getAllCinemas() {
        List<Cinema> cinemas = cinemaRepository.findAll();

        List<CinemaInfoDTO> dto = cinemas.stream()
                .map(cinema -> modelMapper.map(cinema, CinemaInfoDTO.class))
                .collect(Collectors.toList());

        for (Cinema c : cinemas) {
            System.out.println(c.getCity().getName());
        }

        IntStream.range(0, cinemas.size())
                .forEach(index -> dto
                        .get(index)
                        .setCity(modelMapper.map(cinemas.get(index).getCity(), CityInfoDTO.class)));
        return dto;
    }

    public Map<String, MovieProgramDTO> getProgram(int cid) {
        return cinemaDAO.getProgram(cid);
    }
}
