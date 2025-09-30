import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Grid } from '../../interface/grid';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private apiConfigUrl = '/api/config';
  constructor(private http: HttpClient) {}

  // Update grid parameters: number of columns, number of rows, and propagation probability
 updateParameters(cols: number, rows: number, propagationProbability: number): Observable<Grid> {
    const url = `${this.apiConfigUrl}/updateParameters`;
    const body = { cols, rows, propagationProbability };
    return this.http.post<Grid>(url, body);
  }

}