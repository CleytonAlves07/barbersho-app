import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AgendaService, Barbeiro, Agenda } from '../../services/agenda.service';
import { AgendamentoService } from '../../services/agendamento.service';
import { AuthService } from '../../services/auth.service';
import { MatDatepickerModule } from '@angular/material/datepicker';
import {
  MatNativeDateModule,
  MatOptionModule,
  MAT_DATE_LOCALE,
  MAT_DATE_FORMATS,
  DateAdapter,
  MatDateFormats,
} from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { format } from 'date-fns';
import { CommonModule } from '@angular/common';

export const MY_DATE_FORMATS: MatDateFormats = {
  parse: { dateInput: 'dd/MM/yyyy' },
  display: {
    dateInput: 'dd/MM/yyyy',
    monthYearLabel: 'MMMM yyyy',
    dateA11yLabel: 'dd/MM/yyyy',
    monthYearA11yLabel: 'MMMM yyyy',
  },
};

@Component({
  selector: 'app-agenda',
  templateUrl: './agenda.component.html',
  styleUrls: ['./agenda.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatSnackBarModule,
    ReactiveFormsModule,
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'pt-BR' },
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS },
  ],
})
export class AgendaComponent implements OnInit {
  agendaForm: FormGroup;
  barbeiros: Barbeiro[] = [];
  horariosInicio: string[] = [];

  constructor(
    private fb: FormBuilder,
    private agendaService: AgendaService,
    private agendamentoService: AgendamentoService,
    private authService: AuthService,
    private snackBar: MatSnackBar,
    private adapter: DateAdapter<any>
  ) {
    this.agendaForm = this.fb.group({
      data: ['', Validators.required],
      horarioInicio: ['', Validators.required],
      horarioFim: [{ value: '', disabled: true }, Validators.required],
      barbeiroId: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.adapter.setLocale('pt-BR');
    this.carregarBarbeiros();
    this.horariosInicio = this.gerarHorariosInicio();

    this.agendaForm.get('horarioInicio')?.valueChanges.subscribe((inicio) => {
      if (inicio) {
        const [hours, minutes] = inicio.split(':').map(Number);
        const fim = new Date();
        fim.setHours(hours + 1, minutes);
        const horarioFim = fim.toTimeString().substring(0, 5);
        this.agendaForm.get('horarioFim')?.setValue(horarioFim);
        this.agendaForm.get('horarioFim')?.enable();
      } else {
        this.agendaForm.get('horarioFim')?.setValue('');
        this.agendaForm.get('horarioFim')?.disable();
      }
    });
  }

  carregarBarbeiros(): void {
    this.agendaService.getBarbeiros().subscribe((barbeiros) => {
      this.barbeiros = barbeiros;
    });
  }

  salvarAgenda(): void {
    if (this.agendaForm.valid) {
      const dataSelecionada = this.agendaForm.value.data;
      const dataObj = dataSelecionada instanceof Date ? dataSelecionada : new Date(dataSelecionada);
      const dataFormatada = format(dataObj, 'yyyy-MM-dd');

      const novaAgenda: Agenda = {
        data: dataFormatada,
        barbeiroId: this.agendaForm.value.barbeiroId,
        horarioInicio: `${dataFormatada}T${this.agendaForm.value.horarioInicio}:00`,
        horarioFim: `${dataFormatada}T${this.agendaForm.value.horarioFim}:00`,
      };

      this.agendaService.create(novaAgenda).subscribe({
        next: (agendaCriada) => {
          this.criarAgendamento(agendaCriada.id!);
          this.snackBar.open(
            '✅ Horário agendado com sucesso com nosso barbeiro!',
            'Fechar',
            {
              duration: 6000,
              horizontalPosition: 'center',
              verticalPosition: 'top',
              panelClass: ['snackbar-sucesso'],
            }
          );
          this.agendaForm.reset();
          this.agendaForm.get('horarioFim')?.disable();
        },
        error: (err) => console.error('Erro ao criar agenda:', err),
      });
    } else {
      console.log('Formulário inválido');
    }
  }

  criarAgendamento(agendaId: string): void {
    const clienteId = this.authService.getUserIdFromToken();

    if (!clienteId) {
      console.error('ID do cliente não encontrado no token!');
      return;
    }

    const novoAgendamento = {
      dataHora: new Date().toISOString(),
      status: 'PENDENTE' as 'PENDENTE',
      clienteId,
      agendaId,
    };
    console.log("🚀 ~ AgendaComponent ~ criarAgendamento ~ novoAgendamento:", novoAgendamento);

    this.agendamentoService.create(novoAgendamento).subscribe({
      next: () => {
        console.log('Agendamento criado com sucesso!');
      },
      error: (err) => console.error('Erro ao criar agendamento:', err),
    });
  }

  gerarHorariosInicio(): string[] {
    const horarios: string[] = [];
    for (let hora = 8; hora <= 17; hora++) {
      const horaFormatada = hora.toString().padStart(2, '0') + ':00';
      horarios.push(horaFormatada);
    }
    return horarios;
  }
}
