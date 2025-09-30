package com.fire.simulation.dto;

public class ConfigurationDto {
    private int height;
    private int width;
    private double probability;
    private int[][] departureCases;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public int[][] getDepartureCases() {
        return departureCases;
    }
    
    public void setDepartureCases(int[][] departureCases) {
        this.departureCases = departureCases;
    }
}
