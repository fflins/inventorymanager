import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produto } from '../models/produto.model';

@Injectable({
  providedIn: 'root',
})
export class ProdutoService {
  private apiUrl = 'http://localhost:8080/produtos'; 

  constructor(private http: HttpClient) {}

  /**
   * Obtém o token armazenado no localStorage.
   * @returns O token armazenado ou null se não estiver presente.
   */
  private getToken(): string | null {
    return localStorage.getItem('token');
  }

  /**
   * Cria os headers HTTP com o token de autenticação, se disponível.
   * @returns Um objeto HttpHeaders com o token configurado, se existir.
   */
  private createHeaders(): HttpHeaders {
    const token = this.getToken();
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  /**
   * Insere um novo produto na API.
   * @param produto O objeto do tipo Produto a ser inserido.
   * @returns Um Observable com a resposta da API.
   */
  inserirProduto(produto: Produto): Observable<any> {
    const headers = this.createHeaders();
    return this.http.post<any>(this.apiUrl, produto, { headers });
  }

  /**
   * Consulta a lista de produtos cadastrados.
   * @returns Um Observable contendo a lista de produtos.
   */
  consultarProdutos(): Observable<Produto[]> {
    const headers = this.createHeaders();
    return this.http.get<Produto[]>(this.apiUrl, { headers });
  }

  /**
   * Obtém um produto específico pelo código.
   * @param codigo O código do produto a ser consultado.
   * @returns Um Observable com o produto correspondente.
   */
  obterProdutoPorId(codigo: string): Observable<Produto> {
    const url = `${this.apiUrl}/${codigo}`;
    const headers = this.createHeaders();
    return this.http.get<Produto>(url, { headers });
  }

  /**
   * Atualiza as informações de um produto.
   * @param produto O objeto do tipo Produto contendo os dados atualizados.
   * @returns Um Observable com o produto atualizado.
   */
  atualizarProduto(produto: Produto): Observable<Produto> {
    const url = `${this.apiUrl}/${produto.codigo}`;
    const headers = this.createHeaders();
    return this.http.put<Produto>(url, produto, { headers });
  }

  /**
   * Remove um produto pelo código e quantidade especificada.
   * @param codigo O código do produto a ser removido.
   * @param quantidade A quantidade a ser removida.
   * @returns Um Observable com a resposta da API.
   */
  removerProduto(codigo: string, quantidade: number): Observable<any> {
    const url = `${this.apiUrl}/${codigo}?quantidade=${quantidade}`;
    const headers = this.createHeaders();
    return this.http.delete(url, { headers, observe: 'response' });
  }

  /**
   * Faz o download de um relatório de produtos no formato XLSX.
   * @returns Um Observable com o Blob do relatório gerado.
   */
  baixarRelatorio(): Observable<Blob> {
    const url = `${this.apiUrl}/relatorio`; 
    const headers = this.createHeaders();
    return this.http.get(url, {
      headers,
      responseType: 'blob', 
    });
  }

  /**
   * Exporta produtos em formato JSON ou XML.
   * @param formato O formato do relatório (e.g., 'json', 'xml').
   * @returns Um Observable com o Blob do arquivo gerado.
   */
  exportarProdutos(formato: 'json' | 'xml'): Observable<Blob> {
    const url = `${this.apiUrl}/exportar?formato=${formato}`;
    const headers = this.createHeaders();
    return this.http.get(url, {
      headers,
      responseType: 'blob', 
    });
  }
}
