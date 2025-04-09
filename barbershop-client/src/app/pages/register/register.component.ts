import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  form: FormGroup;

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.form = this.fb.group({
      nome: [''],
      username: [''],
      email: [''],
      telefone: [''],
      password: ['']
    });
  }

  onSubmit() {
    this.auth.register(this.form.value).subscribe({
      next: () => this.router.navigate(['/login']),
      error: () => alert('Erro ao registrar')
    });
  }
}
