import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { NgIf } from '@angular/common';
import { CommonModule } from '@angular/common';

export interface DialogData {
}

@Component({
  selector: 'app-end-simulation-dialog',
  imports: [NgIf,CommonModule],
  templateUrl: './end-simulation-dialog.component.html',
  styleUrl: './end-simulation-dialog.component.css'
})
export class EndSimulationDialogComponent {

  result: string;
  percentageOfTrees: number | undefined;

  constructor(
    public dialogRef: MatDialogRef<EndSimulationDialogComponent>,
   @Inject(MAT_DIALOG_DATA) data: DialogData) {
      this.result = (data as any).result;
      this.percentageOfTrees = (data as any).percentageOfTrees;
    }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
