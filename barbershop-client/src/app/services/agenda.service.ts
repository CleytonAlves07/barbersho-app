import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

export interface Usuario {
  id: string;
  nome: string;
  username: string;
  email: string;
  telefone: string;
  role: string;
}

export interface Barbeiro {
  id: string;
  nome: string;
}

export interface Agenda {
  id?: string;
  data: string;
  horarioInicio: string;
  horarioFim: string;
  barbeiro?: Barbeiro;
  barbeiroId?: string;
}

@Injectable({ providedIn: 'root' })
export class AgendaService {
  
  private apiUrl = 'http://localhost:8080/api/agendas';
  private apiUrlBarbeiro = 'http://localhost:8080/api/usuarios';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Agenda[]> {
    return this.http.get<Agenda[]>(this.apiUrl);
  }

  getById(id: string): Observable<Agenda> {
    return this.http.get<Agenda>(`${this.apiUrl}/${id}`);
  }

  create(agenda: Agenda): Observable<Agenda> {
    console.log(agenda);
    return this.http.post<Agenda>(this.apiUrl, agenda);
  }

  update(id: string, agenda: Agenda): Observable<Agenda> {
    return this.http.put<Agenda>(`${this.apiUrl}/${id}`, agenda);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getBarbeiros(): Observable<Barbeiro[]> {

    let barbeiros = this.http.get<Usuario[]>(this.apiUrlBarbeiro)
      .pipe(
        map((usuarios: Usuario[]) =>
          usuarios
            .filter((usuario) => usuario.role === 'BARBEIRO')
            .map((barbeiro) => ({
              id: barbeiro.id,
              nome: barbeiro.nome,
            }))
        )
    );
    console.log(barbeiros);
    return barbeiros;
  }

}
