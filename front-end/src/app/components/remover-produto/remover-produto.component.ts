

import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProdutoService } from '../../services/produto.service';
import { CommonModule } from '@angular/common';
import { Location } from '@angular/common';
import { HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'app-remover-produto',
  templateUrl: './remover-produto.component.html',
  styleUrl: '../operations.components.style.css',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule,],
})



export class RemoverProdutoComponent {
  removerForm: FormGroup;
  feedbackMessage: string = '';


  constructor(private fb: FormBuilder, private produtoService: ProdutoService, private location: Location) {
    this.removerForm = this.fb.group({
      codigo: ['', Validators.required],
      quantidade: [1, [Validators.required, Validators.min(1)]],  
    });
    

  }

  onSubmit() {
    const codigo = this.removerForm.get('codigo')?.value;
    const quantidade = this.removerForm.get('quantidade')?.value;
  
    if (!quantidade || quantidade <= 0) {
      this.feedbackMessage = 'Informe uma quantidade válida para remover';
      return;
    }
  
    this.produtoService.removerProduto(codigo, quantidade).subscribe({
      next: (response) => {
        console.log('Remoção realizada:', response);
        this.feedbackMessage = 'Produto removido com sucesso!';
        this.removerForm.reset();
      },
      error: (err) => {
        console.error('Erro na remoção:', err);
        this.feedbackMessage = err.error?.message || 'Erro ao remover produto';
      }
    });
  }

  
  voltar(): void {
    this.location.back();
  }


}
