package com.fire.simulation.controllers;

import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fire.simulation.dto.ResponseDto;
import com.fire.simulation.entities.Grid;
import com.fire.simulation.service.SimulationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value="/interface", produces = MediaType.APPLICATION_JSON_VALUE)
public class SimulationController {
    public SimulationService simulationService;

    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }
    
   
    @GetMapping
    public ResponseDto getInitGrid() throws Exception {
        System.out.println("init");
        Grid grid = simulationService.init();
        return new ResponseDto(grid, simulationService.getProbability());
    }

    @GetMapping("/step")
    public Grid getStep() {
        System.out.println("step");
        return simulationService.step();
    }

    @PostMapping("/setFire")
    public Grid setCaseToFire(@RequestBody Map<String, Integer> body) {
        int row = body.get("row");
        int col = body.get("col");
        return simulationService.setCaseToFire(row, col);
    }

}