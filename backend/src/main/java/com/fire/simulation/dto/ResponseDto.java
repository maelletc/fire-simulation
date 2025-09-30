package com.fire.simulation.dto;

import com.fire.simulation.entities.Grid;

public class ResponseDto {
    private Grid grid;
    private double probability;

    public ResponseDto(Grid grid, double probability) {
        this.grid = grid;
        this.probability = probability;
    }

    public Grid getGrid() {
        return grid;
    }

    public double getProbability() {
        return probability;
    }
}
