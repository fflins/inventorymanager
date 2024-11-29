//inserir-produto.component.ts

import { Component } from '@angular/core';
import { ProdutoService } from '../../services/produto.service';
import { Location } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Produto } from '../../models/produto.model';

@Component({
  selector: 'app-inserir-produto',
  templateUrl: './inserir-produto.component.html',
  styleUrl: '../operations.components.style.css',
  standalone: true,
  imports: [ReactiveFormsModule]
})
export class InserirProdutoComponent {
  produtoForm: FormGroup;

  

  constructor(private produtoService: ProdutoService, private fb: FormBuilder, private location: Location) {
    this.produtoForm = this.fb.group({
      nome: ['', Validators.required],
      descricao: ['', Validators.required],
      codigo: ['', Validators.required],
      quantidade: [0, [Validators.required, Validators.min(1)]], 
    });
  }

  // Método para voltar à página anterior
  voltar(): void {
    this.location.back();
  }

  onSubmit() {
    if (this.produtoForm.valid) {
      const produto: Produto = this.produtoForm.value;
      this.produtoService.inserirProduto(produto).subscribe(
        (response) => {
          alert('Produto inserido com sucesso!');
          this.produtoForm.reset();
        },
        (error) => {
          alert('Erro!');
          console.error(error);
        }
     );
    }
  }
}