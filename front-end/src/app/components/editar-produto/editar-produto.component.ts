import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { ProdutoService } from '../../services/produto.service';
import { Produto } from '../../models/produto.model';
import { Location } from '@angular/common';

@Component({
  selector: 'app-editar-produto',
  templateUrl: './editar-produto.component.html',
  styleUrls: ['../table.components.style.css'],
  standalone: true,
  imports: [CommonModule, FormsModule] 
})
export class EditarProdutoComponent implements OnInit {
  produtos: Produto[] = [];
  produtoEmEdicao: Produto | null = null;

  constructor(
    private produtoService: ProdutoService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.carregarProdutos();
  }

  carregarProdutos(): void {
    this.produtoService.consultarProdutos().subscribe((produtos) => {
      this.produtos = produtos;
    });
  }

  selecionarProduto(produto: Produto): void {
    this.produtoEmEdicao = { ...produto };
  }

  atualizarProduto(): void {
    if (this.produtoEmEdicao) {
      this.produtoService.atualizarProduto(this.produtoEmEdicao).subscribe(() => {
        this.carregarProdutos();
        this.cancelarEdicao();
      });
    }
  }

  cancelarEdicao(): void {
    this.produtoEmEdicao = null;
  }

  voltar(): void {
    this.location.back();
  }
}
