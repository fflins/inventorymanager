package com.app.projetoGP.model;

import jakarta.persistence.*;

/**
 * Representa um produto na aplicação.
 * Cada produto possui um código único, nome, descrição e quantidade em estoque.
 */
@Entity
public class Produto {

    /** Código identificador único do produto. */
    @Id
    private Long codigo;

    /** Atributos do produto. */
    private String nome;
    private String descricao;
    private int quantidade;

    /**
     * Construtor padrão sem argumentos.
     */
    public Produto() {}

    /**
     * Construtor completo para criar um novo produto com todos os atributos.
     *
     * @param codigo o código único do produto
     * @param nome o nome do produto
     * @param descricao a descrição do produto
     * @param quantidade a quantidade disponível em estoque
     */
    public Produto(Long codigo, String nome, String descricao, int quantidade) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    /**
     * Obtém o código do produto.
     *
     * @return o código do produto
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Define o código do produto.
     *
     * @param codigo o código único a ser definido para o produto
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtém o nome do produto.
     *
     * @return o nome do produto
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto.
     *
     * @param nome o nome a ser definido para o produto
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a descrição do produto.
     *
     * @return a descrição do produto
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do produto.
     *
     * @param descricao a descrição a ser definida para o produto
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a quantidade disponível em estoque do produto.
     *
     * @return a quantidade do produto em estoque
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade disponível em estoque do produto.
     *
     * @param quantidade a quantidade a ser definida para o produto em estoque
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
