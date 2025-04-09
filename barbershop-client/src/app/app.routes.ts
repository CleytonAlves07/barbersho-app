import { Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { LayoutPadraoComponent } from './layouts/layout-padrao/layout-padrao.component';
import { LayoutLimpoComponent } from './layouts/layout-limpo/layout-limpo.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutPadraoComponent,
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./components/home/home.component').then(m => m.HomeComponent)
      },
      {
        path: 'agenda',
        loadComponent: () =>
          import('./components/agenda/agenda.component').then(m => m.AgendaComponent),
        canActivate: [AuthGuard]
      },
      {
        path: 'agendamento',
        loadComponent: () =>
          import('./components/agendamento/agendamento.component').then(m => m.AgendamentoComponent),
        canActivate: [AuthGuard]
      },
      {
        path: 'sobre',
        loadComponent: () =>
          import('./pages/about/about.component').then(m => m.AboutComponent)
      }
    ]
  },
  {
    path: '',
    component: LayoutLimpoComponent,
    children: [
      {
        path: 'login',
        loadComponent: () =>
          import('./pages/login/login.component').then(m => m.LoginComponent)
      },
      {
        path: 'register',
        loadComponent: () =>
          import('./pages/register/register.component').then(m => m.RegisterComponent)
      }
    ]
  },
  {
    path: '**',
    redirectTo: ''
  }
];

