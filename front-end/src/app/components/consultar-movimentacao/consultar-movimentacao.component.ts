import { Component, OnInit } from '@angular/core';
import { MovimentacaoService } from '../../services/movimentacao.service';
import { CommonModule } from '@angular/common';
import { Movimentacao } from '../../models/movimentacao.model';
import { DatePipe } from '@angular/common';
import { Location } from '@angular/common';

@Component({
  selector: 'app-consultar-movimentacoes',
  templateUrl: './consultar-movimentacao.component.html',
  styleUrls: ['../table.components.style.css'],
  standalone: true,
  imports: [CommonModule, DatePipe],
})
export class ConsultarMovimentacaoComponent implements OnInit {
  movimentacoes: Movimentacao[] = [];

  constructor(private movimentacaoService: MovimentacaoService, private location: Location) {}

  voltar(): void {
    this.location.back();
  }

  ngOnInit(): void {
    this.consultarMovimentacoes();
  }

  consultarMovimentacoes() {
    this.movimentacaoService.consultarMovimentacoes().subscribe(movimentacoes => {
      this.movimentacoes = movimentacoes.map(mov => ({
        ...mov,
        dataHora: new Date(mov.dataHora) 
      }));
    });
  }

  limparHistorico(): void {
    this.movimentacaoService.limparMovimentacoes().subscribe(() => {
      this.movimentacoes = []; 
      alert('Histórico de movimentações limpo com sucesso!');
    }, error => {
      console.error('Erro ao limpar histórico:', error);
      alert('Erro ao limpar histórico.');
    });
  }
}
