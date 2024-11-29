package com.app.projetoGP.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.projetoGP.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

/**
 * Serviço para manipulação de tokens JWT, incluindo geração e validação.
 */
@Service
public class TokenService {
    
    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Gera um token JWT para o usuário especificado.
     */
        public String generateToken(Usuario user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withClaim("role", user.getRole().name()) // Add role to the token
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token", exception);
        }
    }

    /**
     * Valida o token JWT e retorna o assunto (usuário) se válido.
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                      .withIssuer("auth-api")
                      .build()
                      .verify(token)
                      .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    /**
     * Gera a data de expiração do token com base na hora atual.
     */
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
