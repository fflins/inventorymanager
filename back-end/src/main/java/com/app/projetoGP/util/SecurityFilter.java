package com.app.projetoGP.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.projetoGP.repository.UsuarioRepository;
import com.app.projetoGP.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de segurança responsável por interceptar as requisições e validar tokens de autenticação.
 * 
 * Este filtro é executado uma única vez por requisição, permitindo que a autenticação do usuário
 * seja verificada com base no token JWT (JSON Web Token) enviado no cabeçalho da requisição.
 * 
 * Ao validar o token, o filtro recupera as informações do usuário e as armazena no contexto de
 * segurança do Spring, permitindo que as informações do usuário autenticado sejam acessadas
 * em outros componentes da aplicação.
 * 
 * @see TokenService
 * @see UsuarioRepository
 */
@Component
public class SecurityFilter extends OncePerRequestFilter{

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository userRepository;

    /**
     * Intercepta a requisição e valida o token de autenticação.
     * 
     * Este método é chamado para cada requisição. Ele tenta recuperar o token do cabeçalho
     * "Authorization", valida o token e, se for válido, armazena as informações do usuário
     * no contexto de segurança.
     * 
     * @param request a requisição HTTP recebida.
     * @param response a resposta HTTP a ser enviada.
     * @param filterChain a cadeia de filtros a ser processada.
     * @throws ServletException se ocorrer um erro de servlet durante o processamento.
     * @throws IOException se ocorrer um erro de entrada/saída durante o processamento.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        var token = this.recoverToken(request);
        if (token!=null){
            var login = tokenService.validateToken(token);
            UserDetails user = userRepository.findByLogin(login);

            System.out.println("Token: " + token);
            System.out.println("Login: " + login);

            var authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities()); 
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Recupera o token JWT do cabeçalho da requisição.
     * 
     * Este método extrai o token do cabeçalho "Authorization". O token é esperado
     * no formato "Bearer <token>", onde "<token>" é o JWT a ser validado.
     * 
     * @param request a requisição HTTP da qual o token deve ser recuperado.
     * @return o token JWT se presente, ou null se não estiver presente.
     */
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if (authHeader==null) return null;
        return authHeader.replace("Bearer ", "");
    }
    
}
