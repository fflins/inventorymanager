package com.app.projetoGP.model;

/**
 * Representa os dados de autenticação para o login de um usuário.
 *
 * @param login o nome de login do usuário
 * @param password a senha do usuário
 */
public record AuthenticationDTO(String login, String password) {

}
