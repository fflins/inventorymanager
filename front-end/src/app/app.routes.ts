import { Routes } from '@angular/router';
import { MenuInicialComponent } from './components/menu-inicial/menu-inicial.component';
import { InserirProdutoComponent } from './components/inserir-produto/inserir-produto.component';
import { ConsultarProdutosComponent } from './components/consultar-produtos/consultar-produtos.component';
import { EditarProdutoComponent } from './components/editar-produto/editar-produto.component';
import { RemoverProdutoComponent } from './components/remover-produto/remover-produto.component';
import { ConsultarMovimentacaoComponent } from './components/consultar-movimentacao/consultar-movimentacao.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrarComponent } from './components/registrar/registrar.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' }, 
  { path: 'login', component: LoginComponent }, 
  { path: 'register', component: RegistrarComponent }, 
  { 
    path: 'menu-inicial', 
    component: MenuInicialComponent, 
    canActivate: [AuthGuard],
    children: [
      { path: 'inserir-produto', component: InserirProdutoComponent },
      { path: 'consultar-produtos', component: ConsultarProdutosComponent },
      { path: 'editar-produto', component: EditarProdutoComponent },
      { path: 'remover-produto', component: RemoverProdutoComponent },
      { path: 'consultar-movimentacoes', component: ConsultarMovimentacaoComponent },
    ]
  }
];