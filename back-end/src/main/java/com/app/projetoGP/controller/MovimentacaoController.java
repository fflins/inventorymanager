package com.app.projetoGP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app.projetoGP.model.Movimentacao;
import com.app.projetoGP.services.MovimentacaoService;

import java.util.List;

/**
 * Controlador responsável pela gestão das movimentações.
 * 
 * Este controlador fornece endpoints para consultar, inserir e deletar movimentações
 * no sistema. As movimentações representam as entradas e saídas de produtos.
 */
@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    /**
     * Endpoint para consultar todas as movimentações.
     * 
     * Este método retorna uma lista de todas as movimentações registradas no sistema.
     * 
     * @return uma lista de objetos {@link Movimentacao} representando as movimentações.
     */
    @GetMapping
    public List<Movimentacao> consultarMovimentacoes() {
        return movimentacaoService.consultarMovimentacoes();
    }

    /**
     * Endpoint para inserir uma nova movimentação.
     * 
     * Este método recebe um objeto {@link Movimentacao} no corpo da requisição e o 
     * salva no sistema.
     * 
     * @param movimentacao objeto que contém os dados da movimentação a ser inserida.
     * @return o objeto {@link Movimentacao} que foi salvo, incluindo o ID gerado.
     */
    @PostMapping
    public Movimentacao inserirMovimentacao(@RequestBody Movimentacao movimentacao) {
        return movimentacaoService.saveMovimentacao(movimentacao);
    }

    /**
     * Endpoint para deletar uma movimentação pelo ID.
     * 
     * Este método remove a movimentação correspondente ao ID fornecido na URL.
     * 
     * @param id o ID da movimentação a ser deletada.
     */
    @DeleteMapping("/{id}")
    public void deleteMovimentacao(@PathVariable Long id) {
        movimentacaoService.deleteMovimentacao(id);
    }

    /**
     * Endpoint para limpar todas as movimentações.
     * 
     * Este método remove todas as movimentações do banco de dados.
     */
    @DeleteMapping
    public void limparMovimentacoes() {
        movimentacaoService.limparMovimentacoes();
    }
}
