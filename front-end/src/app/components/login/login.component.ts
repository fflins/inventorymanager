import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  standalone:true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule]

})
export class LoginComponent {
  login = '';
  password = '';
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLogin(): void {
    this.authService.login({ login: this.login, password: this.password }).subscribe({
      next: (response) => {
        this.authService.saveToken(response.token); 
        console.log(response.token)
        this.router.navigate(['/menu-inicial']); 

      },
      error: (error) => {
        alert("Login ou Senha inválidos")
      }
    });
  }

  navigateToRegister(): void {
    this.router.navigate(['/register']); 
  }
}