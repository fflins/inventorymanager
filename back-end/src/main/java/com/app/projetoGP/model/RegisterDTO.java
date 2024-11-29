package com.app.projetoGP.model;

/**
 * Representa os dados de registro de um novo usuário.
 *
 * @param login o nome de login do usuário
 * @param password a senha do usuário
 * @param role o cargo do usuário
 */
public record RegisterDTO(String login, String password, Role role) {

}
