import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './registrar.component.html',
  styleUrls: ['./registrar.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class RegistrarComponent {
  login = '';
  password = '';
  role = '';
  successMessage = '';
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  onRegister(): void {
    const user = {
      login: this.login,
      password: this.password,
      role: this.role 
    };
  
    this.authService.register(user).subscribe({
      next: (response) => {
        console.log('Resposta da API:', response); 
        this.router.navigate(['/login']); 
      },
      error: (error) => {
        console.error('Erro ao registrar usuário:', error); 
        this.errorMessage = error.error.message || 'Erro ao registrar usuário'; 
      }
    });
  }
  
  navigateToLogin(): void {
    this.router.navigate(['/login']); 
  }
  
}
