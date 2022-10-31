package com.kinoarena.kinoarena.controller;

import com.kinoarena.kinoarena.model.DTOs.projection.FilterProjectionsRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.projection.response.ProjectionInfoDTO;
import com.kinoarena.kinoarena.services.ProjectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectionController extends AbstractController {

    private final ProjectionService projectionService;
    @GetMapping("/test")
    public int test() {
        return 5;
    } //TODO

    //todo tozi url e pod golqm vurpros :DD
    @GetMapping("/projections/filter")
    public List<ProjectionInfoDTO> filterProjections(@RequestBody FilterProjectionsRequestDTO request){
        return projectionService.filterProjections(request);
    }

    @GetMapping("/brands/{brand}/movies")
    public List<ProjectionInfoDTO> getBrandProjections(@PathVariable String brand) {
        return projectionService.getBrandProjections(brand);
    }

    @GetMapping("/brands/{brand}/movies/{cinema}")
    public List<ProjectionInfoDTO> filterBrandProjections(@PathVariable String brand, @PathVariable String cinema) {

        return projectionService.filterBrandProjectionsByCinema(brand, cinema);
    }
}
