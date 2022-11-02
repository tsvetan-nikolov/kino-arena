package com.kinoarena.kinoarena.model.DTOs.cinema;

import com.kinoarena.kinoarena.model.DTOs.city.CityInfoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaInfoResponseDTO {
    private int id;
    private String name;
    private String address;
    private CityInfoResponseDTO city;
}
