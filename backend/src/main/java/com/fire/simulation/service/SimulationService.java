package com.fire.simulation.service;

import org.springframework.stereotype.Service;
import com.fire.simulation.dto.ConfigurationDto;
import com.fire.simulation.entities.CellState;
import com.fire.simulation.entities.Grid;

@Service
public class SimulationService {

    private Grid grid;
    private double propagationProbability=0.0;
     private final ConfigurationService configurationService;

    public SimulationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }


    /**
     * Initialization of the grid
     */
    public Grid init() throws Exception {

        ConfigurationDto config = configurationService.loadConfiguration();
        int height = config.getHeight();
        int width = config.getWidth();
        this.propagationProbability = config.getProbability();

        Grid grid = new Grid(height, width);
        int[][] departureCases = config.getDepartureCases();

        // Verify if departure cases are within bounds and set them on fire
        for (int[] cell : departureCases) {
            int x = cell[0];
            int y = cell[1];
            if (x >= 0 && x < height && y >= 0 && y < width) {
                grid.getGrid()[x][y] = CellState.FIRE;
            }
        }

        this.grid = grid;
        return this.grid;
    }

    /**
     * One step of propagation of the fire
     */
    public Grid step() {
        if (this.grid == null) {
            throw new IllegalStateException("Grid not initialized. Call init() first.");
        }

        int height = grid.getGrid().length;
        int width = grid.getGrid()[0].length;
        Grid newGrid = new Grid(height, width);

        for (int i = 0; i < height; i++) {
            System.arraycopy(grid.getGrid()[i], 0, newGrid.getGrid()[i], 0, width);
        }

        double probability = this.propagationProbability;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid.getGrid()[i][j] == CellState.FIRE) {
                    newGrid.getGrid()[i][j] = CellState.ASH;
                    propagateFire(grid, newGrid, i, j, probability);
                }
            }
        }

        this.grid = newGrid;
        return this.grid;
    }

    /**
     * Propagation of the fire to adjacent cells
     */
    private void propagateFire(Grid oldGrid, Grid newGrid, int x, int y, double probability) {
        int height = oldGrid.getGrid().length;
        int width = oldGrid.getGrid()[0].length;

        int[][] directions = { {-1,0}, {1,0}, {0,-1}, {0,1} }; // up, down, left, right
        for (int[] d : directions) {
            int nx = x + d[0];
            int ny = y + d[1];
            if (nx >= 0 && nx < height && ny >= 0 && ny < width) {
                if (oldGrid.getGrid()[nx][ny] == CellState.TREE && Math.random() < probability) {
                    newGrid.getGrid()[nx][ny] = CellState.FIRE;
                }
            }
        }
    }

    /**
     * Set a specific case on fire
     */
    public Grid setCaseToFire(int row, int col) {
        if (this.grid == null) {
            throw new IllegalStateException("Grid not initialized. Call init() first.");
        }

        int height = grid.getGrid().length;
        int width = grid.getGrid()[0].length;

        if (row < 0 || row >= height || col < 0 || col >= width) {
            throw new IllegalArgumentException("Row or column out of bounds");
        }

        if (grid.getGrid()[row][col] == CellState.TREE) {
            grid.getGrid()[row][col] = CellState.FIRE;
        }
        
        return this.grid;
    }

    public Grid setParameters(int rows, int cols, double propagationProbability) {
        this.propagationProbability = propagationProbability;

        if (this.grid == null) {
            this.grid = new Grid(rows, cols);
            fillGridWithTree(this.grid);
        } else {
            Grid newGrid = new Grid(rows, cols);
            fillGridWithTree(newGrid);
            int minRows = Math.min(rows, this.grid.getGrid().length);
            int minCols = Math.min(cols, this.grid.getGrid()[0].length);
            for (int i = 0; i < minRows; i++) {
                System.arraycopy(this.grid.getGrid()[i], 0, newGrid.getGrid()[i], 0, minCols);
            }
            this.grid = newGrid;
        }

        return this.grid;
    }

    private void fillGridWithTree(Grid grid) {
        CellState[][] cells = grid.getGrid();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == null) cells[i][j] = CellState.TREE;
            }
        }
    }

    public double getProbability() {
        return this.propagationProbability;
    }

}
