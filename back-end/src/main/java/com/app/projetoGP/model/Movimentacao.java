package com.app.projetoGP.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Representa uma movimentação de estoque, contendo informações sobre a ação realizada,
 * incluindo o tipo de movimentação, a quantidade e a data e hora da ação.
 */
@Entity
public class Movimentacao {

    /** Código identificador único da movimentação. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    private String nome;
    private String tipo;
    private int quantidade;
    private LocalDateTime dataHora;

    /**
     * Construtor completo para criar uma nova movimentação com todos os atributos especificados.
     *
     * @param codigo o código único da movimentação
     * @param nome o nome do produto relacionado à movimentação
     * @param tipo o tipo de movimentação (ex.: "Entrada" ou "Saída")
     * @param quantidade a quantidade de produtos movimentados
     * @param dataHora a data e hora em que a movimentação ocorreu
     */
    public Movimentacao(Long codigo, String nome, String tipo, int quantidade, LocalDateTime dataHora) {
        this.codigo = codigo;
        this.nome = nome;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.dataHora = dataHora;
    }

    /**
     * Construtor padrão sem argumentos.
     */
    public Movimentacao() {
    }

    /**
     * Obtém o código da movimentação.
     *
     * @return o código da movimentação
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Define o código da movimentação.
     *
     * @param codigo o código único a ser definido para a movimentação
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtém o nome do produto relacionado à movimentação.
     *
     * @return o nome do produto
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto relacionado à movimentação.
     *
     * @param nome o nome a ser definido para o produto
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o tipo de movimentação.
     *
     * @return o tipo de movimentação (ex.: "Entrada" ou "Saída")
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo de movimentação.
     *
     * @param tipo o tipo de movimentação (ex.: "Entrada" ou "Saída")
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtém a quantidade de produtos movimentados.
     *
     * @return a quantidade de produtos movimentados
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade de produtos movimentados.
     *
     * @param quantidade a quantidade de produtos a ser definida para a movimentação
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Obtém a data e hora da movimentação.
     *
     * @return a data e hora da movimentação
     */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Define a data e hora da movimentação.
     *
     * @param dataHora a data e hora a ser definida para a movimentação
     */
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

}
