package com.app.projetoGP.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Representa um usuário do sistema, implementando a interface UserDetails
 * para integração com o Spring Security.
 */
@Entity
public class Usuario implements UserDetails {

    /** Identificador único do usuário. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome de login único do usuário. */
    @Column(nullable = false, unique = true)
    private String login;

    /** Senha do usuário. */
    private String password;

    /** Papel de autorização do usuário (ex.: ADMIN ou USUARIO). */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Construtor padrão sem argumentos.
     */
    public Usuario() {
    }

    /**
     * Construtor que inicializa o login, senha e papel do usuário.
     *
     * @param login o nome de login do usuário
     * @param password a senha do usuário
     * @param role o papel de autorização do usuário
     */
    public Usuario(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    /**
     * Construtor que inicializa todos os atributos do usuário.
     *
     * @param id o identificador único do usuário
     * @param login o nome de login do usuário
     * @param password a senha do usuário
     * @param role o papel de autorização do usuário
     */
    public Usuario(Long id, String login, String password, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    /**
     * Obtém o identificador do usuário.
     *
     * @return o identificador do usuário
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o identificador do usuário.
     *
     * @param id o identificador único a ser definido para o usuário
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém as autoridades concedidas ao usuário com base em seu papel.
     *
     * @return uma coleção de autoridades concedidas ao usuário
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == Role.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USUARIO"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
        }
    }

    /**
     * Obtém o nome de login do usuário.
     *
     * @return o nome de login do usuário
     */
    @Override
    public String getUsername() {
        return login;
    }

    /**
     * Obtém a senha do usuário.
     *
     * @return a senha do usuário
     */
    @Override
    public String getPassword() {
        return password;
    }

    public Role getRole(){
        return role;
    }

    /**
     * Indica se a conta do usuário está expirada.
     *
     * @return true, se a conta do usuário não estiver expirada; caso contrário, false
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // Conta nunca expira
    }

    /**
     * Indica se a conta do usuário está bloqueada.
     *
     * @return true, se a conta do usuário não estiver bloqueada; caso contrário, false
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // Conta nunca é bloqueada
    }

    /**
     * Indica se as credenciais do usuário estão expiradas.
     *
     * @return true, se as credenciais do usuário não estiverem expiradas; caso contrário, false
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credenciais nunca expiram
    }

    /**
     * Indica se o usuário está habilitado.
     *
     * @return true, se o usuário estiver habilitado; caso contrário, false
     */
    @Override
    public boolean isEnabled() {
        return true; // Usuário está habilitado
    }

}
