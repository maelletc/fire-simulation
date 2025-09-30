package com.fire.simulation.entities;

public class Grid {
    private CellState[][] grid;

    public Grid(int height, int width) {
        this.grid = new CellState[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.grid[i][j] = CellState.TREE;
            }
        }
    }

    public CellState[][] getGrid() {
        return grid;
    }

}
