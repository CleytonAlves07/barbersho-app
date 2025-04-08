import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Agenda, AgendaService } from '../../services/agenda.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-agenda',
  standalone: true,
  templateUrl: './agenda.component.html',
  styleUrl: './agenda.component.css',
  imports: [CommonModule, ReactiveFormsModule],
})
export class AgendaComponent implements OnInit {
  agendas: Agenda[] = [];
  agendaForm: FormGroup;

  constructor(
    private agendaService: AgendaService,
    private fb: FormBuilder
  ) {
    this.agendaForm = this.fb.group({
      horarioInicio: [''],
      horarioFim: [''],
    });
  }

  ngOnInit(): void {
    this.loadAgendas();
  }

  loadAgendas() {
    this.agendaService.getAll().subscribe({
      next: (data) => (this.agendas = data),
      error: (err) => console.error('Erro ao buscar agendas:', err),
    });
  }

  salvarDisponibilidade() {
    const barbeiroId = '123';
    const novaAgenda: Agenda = {
      ...this.agendaForm.value,
      barbeiroId,
    };

    this.agendaService.create(novaAgenda).subscribe({
      next: () => {
        this.agendaForm.reset();
        this.loadAgendas();
      },
      error: (err) => console.error('Erro ao criar agenda:', err),
    });
  }
}
