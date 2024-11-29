import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movimentacao } from '../models/movimentacao.model';
import { AuthService } from './auth.service'; 

@Injectable({
  providedIn: 'root',
})
export class MovimentacaoService {
  private apiUrl = 'http://localhost:8080/movimentacoes'; 

  constructor(private http: HttpClient, private authService: AuthService) {}

  
  consultarMovimentacoes(): Observable<Movimentacao[]> {
    const token = this.authService.getToken(); 
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`, 
    });

    return this.http.get<Movimentacao[]>(this.apiUrl, { headers });
  }

  
  limparMovimentacoes(): Observable<void> {
    const token = this.authService.getToken(); 
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`, 
    });
  
    return this.http.delete<void>(this.apiUrl, { headers });
  }
  
}
