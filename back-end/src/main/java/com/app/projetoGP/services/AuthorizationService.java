package com.app.projetoGP.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.app.projetoGP.repository.UsuarioRepository;

/**
 * Serviço de autorização que implementa {@link UserDetailsService} para carregar os detalhes do usuário com base no login.
 */
@Service
public class AuthorizationService implements UserDetailsService {

    private final UsuarioRepository repository;

    /**
     * Construtor que permite a injeção de dependência do repositório de usuários.
     *
     * @param repository repositório de usuários
     */
    public AuthorizationService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }
}
