package com.kinoarena.kinoarena.model.services;

import com.kinoarena.kinoarena.model.DAOs.CinemaDAO;
import com.kinoarena.kinoarena.model.DTOs.cinema.CinemaInfoDTO;
import com.kinoarena.kinoarena.model.DTOs.city.CityInfoDTO;
import com.kinoarena.kinoarena.model.DTOs.movie.MovieProgramDTO;
import com.kinoarena.kinoarena.model.entities.Cinema;
import com.kinoarena.kinoarena.model.repositories.CinemaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CinemaDAO cinemaDAO;
    public List<CinemaInfoDTO> getAllCinemas() {
        List<Cinema> cinemas = cinemaRepository.findAll();

        List<CinemaInfoDTO> dto = cinemas.stream()
                .map(cinema -> modelMapper.map(cinema, CinemaInfoDTO.class))
                .collect(Collectors.toList());

        for (Cinema c : cinemas
             ) {
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
