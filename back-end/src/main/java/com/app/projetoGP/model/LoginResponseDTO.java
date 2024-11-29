package com.app.projetoGP.model;

/**
 * Representa a resposta de autenticação contendo o token JWT.
 *
 * @param token o token de autenticação gerado para o usuário
 */
public record LoginResponseDTO(String token) {

}
