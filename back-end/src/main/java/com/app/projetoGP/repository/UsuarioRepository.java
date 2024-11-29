package com.app.projetoGP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import com.app.projetoGP.model.Usuario;

/**
 * Repositório para a entidade {@link Usuario}, facilitando operações CRUD e consultas específicas.
 * Herda {@link JpaRepository} para transações com o banco de dados.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um usuário com base no login.
     *
     * @param login o nome de login do usuário
     * @return um objeto {@link UserDetails} correspondente ao usuário com o login especificado
     */
    UserDetails findByLogin(String login);
}
