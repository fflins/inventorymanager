package com.app.projetoGP.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.projetoGP.model.Produto;
import com.app.projetoGP.model.Movimentacao;
import com.app.projetoGP.repository.ProdutoRepository;
import com.app.projetoGP.repository.MovimentacaoRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciamento de produtos, incluindo operações de inserção, remoção, atualização e geração de relatórios.
 */
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    public List<Produto> consultarProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> getProdutoById(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto inserirProduto(Produto produto) {
        Produto savedProduto = produtoRepository.save(produto);
    
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setNome(savedProduto.getNome());
        movimentacao.setTipo("Entrada");
        movimentacao.setQuantidade(produto.getQuantidade());
        movimentacao.setDataHora(LocalDateTime.now());
        movimentacaoRepository.save(movimentacao);
    
        return savedProduto;
    }

    public void removerProduto(Long id, int quantidade) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            if (quantidade >= produto.getQuantidade()) {
                produtoRepository.deleteById(id);
            } else {
                produto.setQuantidade(produto.getQuantidade() - quantidade);
                produtoRepository.save(produto);
            }
            Movimentacao movimentacao = new Movimentacao();
            movimentacao.setNome(produto.getNome());
            movimentacao.setTipo("Saída");
            movimentacao.setQuantidade(quantidade);
            movimentacao.setDataHora(LocalDateTime.now());
            movimentacaoRepository.save(movimentacao);
        }
    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produtoExistente = produtoOptional.get();
            produtoExistente.setNome(produtoAtualizado.getNome());
            produtoExistente.setDescricao(produtoAtualizado.getDescricao());
            produtoExistente.setQuantidade(produtoAtualizado.getQuantidade());
            return produtoRepository.save(produtoExistente);
        } else {
            throw new RuntimeException("Produto com ID " + id + " não encontrado");
        }
    }

    public ByteArrayInputStream gerarRelatorioProdutos() throws IOException {
        List<Produto> produtos = produtoRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Produtos");

            // Cabeçalho
            Row headerRow = sheet.createRow(0);
            String[] colunas = {"Código", "Nome", "Descrição", "Quantidade"};
            for (int i = 0; i < colunas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(colunas[i]);
            }

            // Dados dos produtos
            int rowIdx = 1;
            for (Produto produto : produtos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(produto.getCodigo());
                row.createCell(1).setCellValue(produto.getNome());
                row.createCell(2).setCellValue(produto.getDescricao());
                row.createCell(3).setCellValue(produto.getQuantidade());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
