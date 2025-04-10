import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

interface LoginResponse {
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient, private router: Router) { }

  login(credentials: { email: string; password: string }): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap((res) => {
        localStorage.setItem('token', res.token);
      })
    );
  }

  register(data: { nome: string; username: string; email: string; telefone: string; password: string }): Observable<any> {
    return this.http.post('http://localhost:8080/api/usuarios', {
      ...data,
      role: 'CLIENTE'
    });
  }

  getUserIdFromToken(): string | null {
    const token = localStorage.getItem('token');
    if (!token) return null;

    try {
      const decoded: any = jwtDecode(token);
      console.log("🚀 ~ AuthService ~ getUserIdFromToken ~ decoded:", decoded)
      
      return decoded.id || null;
    } catch (error) {
      console.error('Erro ao decodificar o token:', error);
      return null;
    }
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }
}
