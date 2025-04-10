import { Component, OnInit } from '@angular/core';
import { format } from 'date-fns';
import { Agendamento, AgendamentoService } from '../../services/agendamento.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-agendamento',
  templateUrl: './agendamento.component.html',
  styleUrls: ['./agendamento.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class AgendamentoComponent implements OnInit {
  agendamentos: Agendamento[] = [];

  constructor(private agendamentoService: AgendamentoService) { }

  ngOnInit(): void {
    this.carregarAgendamentos();
  }

  carregarAgendamentos(): void {
    this.agendamentoService.getAll().subscribe((dados: Agendamento[]) => {
      this.agendamentos = dados;
    });
  }

  formatarData(data: string | undefined): string {
    if (!data) return '---';
    return format(new Date(data), 'dd/MM/yy');
  }

  formatarDataHora(dataHora: string | undefined): string {
    if (!dataHora) return '---';
    return format(new Date(dataHora), 'HH:mm');
  }
}
