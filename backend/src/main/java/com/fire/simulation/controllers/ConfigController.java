package com.fire.simulation.controllers;

import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fire.simulation.entities.Grid;
import com.fire.simulation.service.SimulationService;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private final SimulationService simulationService;

    public ConfigController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @PostMapping("/updateParameters")
    public Grid updateParameters(@RequestBody Map<String, Object> body) {
        int rows = (Integer) body.get("rows");
        int cols = (Integer) body.get("cols");
        double propagationProbability = ((Number) body.get("propagationProbability")).doubleValue();
        return simulationService.setParameters(rows, cols, propagationProbability);
    }

}
