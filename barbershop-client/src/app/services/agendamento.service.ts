import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Agenda, Barbeiro } from './agenda.service';

export interface Cliente {
  id: string;
  nome: string;
  username: string;
  email: string;
  telefone: string;
  role: string;
}

export interface Agendamento {
  id?: string;
  dataHora: string;
  status: 'PENDENTE' | 'CONFIRMADO' | 'CANCELADO';
  cliente?: Cliente;
  clienteId?: string;
  agenda?: Agenda;
  agendaId?: string;
}

@Injectable({ providedIn: 'root' })
export class AgendamentoService {
  private apiUrl = 'http://localhost:8080/api/agendamentos';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Agendamento[]> {
    return this.http.get<Agendamento[]>(this.apiUrl);
  }

  getById(id: string): Observable<Agendamento> {
    return this.http.get<Agendamento>(`${this.apiUrl}/${id}`);
  }

  create(agendamento: Agendamento): Observable<Agendamento> {
    return this.http.post<Agendamento>(this.apiUrl, agendamento);
  }

  update(id: string, agendamento: Agendamento): Observable<Agendamento> {
    return this.http.put<Agendamento>(`${this.apiUrl}/${id}`, agendamento);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
