package com.app.projetoGP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.projetoGP.model.Produto;
import com.app.projetoGP.services.ProdutoService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Controlador responsável pela gestão dos produtos e geração de relatórios.
 */
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> getAllProdutos() {
        return produtoService.consultarProdutos();
    }

    @GetMapping("/{id}")
    public Produto getProdutoById(@PathVariable Long id) {
        return produtoService.getProdutoById(id).orElse(null);
    }

    @PostMapping
    public Produto createProduto(@RequestBody Produto produto) {
        return produtoService.inserirProduto(produto);
    }

    @PutMapping("/{id}")
    public Produto updateProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        return produtoService.atualizarProduto(id, produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deleteProduto(@PathVariable Long id, @RequestParam int quantidade) {
        produtoService.removerProduto(id, quantidade);
    }

    @GetMapping("/relatorio")
    public ResponseEntity<byte[]> gerarRelatorioProdutos() throws IOException {
        ByteArrayInputStream relatorio = produtoService.gerarRelatorioProdutos();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=produtos.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(relatorio.readAllBytes());
    }
}
