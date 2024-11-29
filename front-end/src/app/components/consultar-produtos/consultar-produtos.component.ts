import { Component, OnInit } from '@angular/core';
import { ProdutoService } from '../../services/produto.service';
import { Produto } from '../../models/produto.model';
import { Location } from '@angular/common';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-consultar-produtos',
  templateUrl: './consultar-produtos.component.html',
  styleUrls: ['../table.components.style.css'],
  standalone: true,
  imports: [CommonModule, FormsModule],
})
export class ConsultarProdutosComponent implements OnInit {
  produtos: Produto[] = [];
  produtosFiltrados: Produto[] = [];
  isFilteredByName: boolean = false;
  isFilteredByQuantity: boolean = false;
  termoBusca: string = '';

  constructor(
    private produtoService: ProdutoService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.consultarProdutos();
  }

  consultarProdutos() {
    this.produtoService.consultarProdutos().subscribe((produtos) => {
      this.produtos = produtos;
      this.produtosFiltrados = produtos;
    });
  }

  ordenarPorNome() {
    if (!this.isFilteredByName) {
      this.produtosFiltrados.sort((a, b) => a.nome.localeCompare(b.nome));
      this.isFilteredByQuantity = false;
    } else {
      this.consultarProdutos();
    }
    this.isFilteredByName = !this.isFilteredByName;
  }

  ordenarPorQuantidade() {
    if (!this.isFilteredByQuantity) {
      this.produtosFiltrados.sort((a, b) => a.quantidade - b.quantidade);
      this.isFilteredByName = false;
    } else {
      this.consultarProdutos();
    }
    this.isFilteredByQuantity = !this.isFilteredByQuantity;
  }

  filtrarProdutos() {
    const termo = this.termoBusca.toLowerCase();
    this.produtosFiltrados = this.produtos.filter(
      (produto) =>
        produto.nome.toLowerCase().includes(termo) ||
        produto.descricao.toLowerCase().includes(termo)
    );
  }

  baixarRelatorio(): void {
    this.produtoService.baixarRelatorio().subscribe((data: Blob) => {
      const blob = new Blob([data], {
        type:
          'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = 'produtos-relatorio.xlsx';
      link.click();
      window.URL.revokeObjectURL(url);
    });
  }

  voltar(): void {
    this.location.back();
  }
}
