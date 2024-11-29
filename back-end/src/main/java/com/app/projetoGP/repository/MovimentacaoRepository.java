package com.app.projetoGP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.projetoGP.model.Movimentacao;

/**
 * Repositório para a entidade {@link Movimentacao}, permitindo operações CRUD.
 * Herda {@link JpaRepository} para suporte a transações com o banco de dados.
 */
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
}
