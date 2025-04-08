import { Component, OnInit } from '@angular/core';
import { Agendamento, AgendamentoService } from '../../services/agendamento.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-agendamento',
  templateUrl: './agendamento.component.html',
  imports: [CommonModule],
  styleUrls: ['./agendamento.component.css']
})
export class AgendamentoComponent implements OnInit {
  agendamentos: Agendamento[] = [];

  constructor(private agendamentoService: AgendamentoService) { }

  ngOnInit(): void {
    this.agendamentoService.getAll().subscribe((res) => (this.agendamentos = res));
  }
}