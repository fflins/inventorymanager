package com.app.projetoGP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.projetoGP.model.Produto;

/**
 * Repositório para a entidade {@link Produto}, fornecendo operações CRUD.
 * Herda {@link JpaRepository} para gerenciamento de transações com o banco de dados.
 */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
