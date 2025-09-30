import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { GridService } from '../../services/grid/grid-service';
import { ConfigService } from '../../services/config/config.service';
import { Grid } from '../../interface/grid';
import { MatDialog } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';
import { EndSimulationDialogComponent } from '../end-simulation-dialog/end-simulation-dialog.component';

@Component({
  selector: 'app-grid',
  standalone: true,
  imports: [CommonModule, MatGridListModule, MatButtonModule, FormsModule],
  templateUrl: './grid.component.html',
  styleUrl: './grid.component.css'
})
export class GridComponent {

  colsNumber: number | undefined ;  
  rowsNumber: number | undefined;
  propagationProbability: number | undefined ;
  updatedPropagationProbability: number | undefined ;
  updatedColsNumber: number | undefined ;
  updatedRowsNumber: number | undefined ;
 
  grid: Grid | undefined;
  isSimulationRunning: boolean = false;
  isStarted: boolean = false;
  stepCount: number = 0;

  constructor(private gridService: GridService,private configService: ConfigService, public dialog: MatDialog) {
    this.initializeGrid();
  }

  get flattenedGrid(): string[] {
    if (!this.grid?.grid) {
      console.warn('Grid data is not yet available or invalid:', this.grid);
      return [];
    }
    const flattened = this.grid.grid.flat();
    return flattened;
  }

  // Initialize the grid and set default parameters
  initializeGrid(callback?: () => void) {
    this.gridService.getInterface().subscribe(data => {
      this.grid = data.grid;
      this.colsNumber = this.grid?.grid[0]?.length ?? 0;
      this.rowsNumber = this.grid?.grid.length ?? 0;
      this.updatedColsNumber = this.colsNumber;
      this.updatedRowsNumber = this.rowsNumber;
      this.isSimulationRunning = false;
      this.stepCount = 0;
      this.propagationProbability = data.probability;
      this.updatedPropagationProbability = this.propagationProbability;
      if (callback) {
        callback();
      }
    });
   
  }

  // Logic to start the simulation automatically
  startSimulation() {
    this.isStarted = true;
    this.isSimulationRunning = true;
    const runSimulation = () => {
      if (!this.isSimulationRunning) {
        return; // Stop the simulation if it's no longer running
      }
      this.nextStep();
      setTimeout(runSimulation, 600); // Schedule the next step
    };
    runSimulation();
  }

  // simulation only one step at a time
  startSimulationByStep() {
    this.isStarted = true;
    this.nextStep();
  }

  // Logic to advance one step in the simulation
  nextStep() {
    this.stepCount++;
    this.gridService.getNextStep().subscribe(data => {
      this.grid=data;
      this.colsNumber = this.grid.grid[0].length;
      this.rowsNumber = this.grid.grid.length;
    });
    let hasTree = false;
    let treeNumber = 0;
    if (this.rowsNumber !== undefined && this.colsNumber !== undefined && this.grid?.grid) {
      for (let i = 0; i < this.rowsNumber; i++) {
        for (let j = 0; j < this.colsNumber; j++) {
          if (this.grid.grid[i][j] === 'TREE') {
            hasTree = true;
            treeNumber++;
          }
          if (this.grid.grid[i][j] === 'FIRE') {
            return; // Simulation is not finished if there is still FIRE
          }
        }
      }
      this.isSimulationRunning=false
    }
    let percentageOfTrees = 0;
    if (this.rowsNumber !== undefined && this.colsNumber !== undefined) {
      percentageOfTrees = (treeNumber / (this.rowsNumber * this.colsNumber)) * 100;
    }
    const dialogRef = this.dialog.open(EndSimulationDialogComponent, {
      width: '250px',
      data: { result: hasTree ? 'TREE' : 'ASH', percentageOfTrees: percentageOfTrees }
    });

    dialogRef.afterClosed().subscribe(() => {
      this.isSimulationRunning = false;
      this.isStarted = false;
      this.initializeGrid();
    });
      
  }

  // user can select a cell to set it on fire
  onCellClick(index: number) {
    if (!this.flattenedGrid || !this.colsNumber) {
      console.warn('Grid not initialized yet');
      return;
    }
    const row = Math.floor(index / this.colsNumber);
    const col = index % this.colsNumber;

    this.gridService.setCaseToFire(row, col).subscribe({
      next: (updatedGrid) => {
        this.grid = updatedGrid;
        this.colsNumber = this.grid.grid[0].length;
        this.rowsNumber = this.grid.grid.length;
      },
      error: (err) => console.error('Error updating grid:', err)
    });
  }

  // for one simulation we can update the parameters such as cols, rows and propagationProbability
  applyChanges() {
    if (this.updatedColsNumber && this.updatedRowsNumber && this.updatedPropagationProbability) {
      this.configService.updateParameters(this.updatedColsNumber, this.updatedRowsNumber, this.updatedPropagationProbability).subscribe({
          next: (updatedGrid) => {
            this.grid = updatedGrid;
            this.colsNumber = this.grid.grid[0].length;
            this.rowsNumber = this.grid.grid.length;
            this.propagationProbability = this.updatedPropagationProbability;
          }
      });
    }
  }

}