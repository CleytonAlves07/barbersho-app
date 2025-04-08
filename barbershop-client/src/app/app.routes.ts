import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AgendaComponent } from './components/agenda/agenda.component';
import { AgendamentoComponent } from './components/agendamento/agendamento.component';
import { AboutComponent } from './pages/about/about.component';


export const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'agenda',
    component: AgendaComponent
  },
  {
    path: 'agendamento',
    component: AgendamentoComponent
  },
  {
    path: 'sobre',
    component: AboutComponent
  },
  {
    path: '**',
    redirectTo: ''
  }
];
