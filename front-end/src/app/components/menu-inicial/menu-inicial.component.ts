import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service'; 

@Component({
  selector: 'app-menu-inicial',
  templateUrl: './menu-inicial.component.html',
  styleUrl: './menu-inicial.component.css',
  standalone: true,
  imports: [RouterModule]
})
export class MenuInicialComponent {

  constructor(
    private router: Router, 
    private authService: AuthService,
  ) {}

  checkAdminAccess(operation: string) {
    const userRole = this.authService.getUserRole();
    
    if (userRole !== 'ADMIN') {
      alert(`Você não tem permissão para ${operation}. Somente administradores podem realizar esta ação.`);
            
      return false;
    }
    return true;
  }

  irParaInserir() {
    if (this.checkAdminAccess('inserir produto')) {
      this.router.navigate(['/menu-inicial/inserir-produto']);
    }
  }

  irParaEditar() {
    if (this.checkAdminAccess('editar produto')) {
      this.router.navigate(['/menu-inicial/editar-produto']);
    }
  }

  irParaRemover() {
    if (this.checkAdminAccess('remover produto')) {
      this.router.navigate(['/menu-inicial/remover-produto']);
    }
  }

  
  irParaConsultar() {
    this.router.navigate(['/menu-inicial/consultar-produtos']);
  }

  irParaHistorico() {
    this.router.navigate(['/menu-inicial/consultar-movimentacoes']);
  }

  sair() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}