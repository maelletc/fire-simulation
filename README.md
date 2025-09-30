# Forest Fire Simulation

## Project Objective

The goal of this project is to implement a simulation of forest fire propagation. The forest is represented as a grid of dimensions height × width, and the simulation progresses step by step in discrete time.

## Simulation Rules

Initially, one or more cells are on fire.

At each time step t:

- A burning cell will turn into ashes and cannot burn again.
- Each of the four adjacent cells has a probability p of catching fire.

The simulation ends when there are no more burning cells.

The grid size, initial burning cells, and fire propagation probability are configurable and stored in a JSON configuration file.

## Run
- **Frontend:**  
  cd frontend
  npm install
  ng serve
- **Backend:**
  mvn clean install
  mvn spring-boot:run

## Technical Stack

- **Backend:** Spring Boot (Java)  
- **Frontend:** Angular  

## Initial Configuration

The starting parameters for the simulation are stored in: /src/main/resources/configuration.json


This file includes:

- Grid dimensions (rows x cols)  
- Fire propagation probability (p)  
- Initial burning cells  

## Usage

Users can run the simulation in two ways:

- **Run directly:** The simulation runs automatically step by step until it finishes.  
- **Step by step:** The user can manually advance the simulation one step at a time.  

Additionally, users can:

- Select different starting cells on fire by clicking on the grid.  
- Adjust the fire propagation probability or grid size for a new simulation.  

## Architecture Overview

### Frontend

- Built with Angular  
- Components, interfaces, and services handle UI rendering and interaction  
- Calls backend APIs to fetch and update the grid state  

### Backend

- Built with Spring Boot (Java)  
- **Controller:** Handles API requests and delegates to services  
- **Services:** Contain the simulation logic and manage entities  

### Workflow

- On initialization, the frontend requests the initial grid state and fire probability from the backend.  
- At each step, the frontend requests the updated grid state from the backend.
