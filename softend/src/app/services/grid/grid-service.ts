import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Grid } from '../../interface/grid';
import { InitResponse } from '../../interface/initResponse';

@Injectable({
  providedIn: 'root'
})
export class GridService {

  private apiUrl = '/api/interface';
  constructor(private http: HttpClient) {}

  // get the initialisation of the grid
  getInterface(): Observable<InitResponse> {
    return this.http.get<InitResponse>(this.apiUrl);
  }
  
  // advance the simulation by one step
  getNextStep(): Observable<Grid> {
    const url = `${this.apiUrl}/step`;
    return this.http.get<Grid>(url);
  }

  // user can select a cell to set it on fire
  setCaseToFire(row: number, col: number): Observable<Grid> {
    const url = `${this.apiUrl}/setFire`;
    const body = { row, col };
    return this.http.post<Grid>(url, body);
  }

}
