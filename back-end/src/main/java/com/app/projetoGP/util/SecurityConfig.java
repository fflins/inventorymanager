package com.app.projetoGP.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/**
 * Classe de configuração de segurança para a aplicação.
 * 
 * Esta classe define a configuração de segurança da API, incluindo a gestão
 * de autenticação, autorização e políticas de CORS. Utiliza o Spring Security
 * para proteger os endpoints da aplicação, permitindo o acesso a diferentes
 * usuários com base em suas funções (roles).
 * 
 * Os principais aspectos configurados nesta classe incluem:
 * <ul>
 *     <li>Desabilitação de CSRF (Cross-Site Request Forgery) para uma API REST
 *     stateless.</li>
 *     <li>Configuração de CORS para permitir requisições de origens específicas.</li>
 *     <li>Definição de um filtro de segurança personalizado para autenticação.</li>
 *     <li>Configuração do gerenciamento de sessões como stateless.</li>
 *     <li>Definição das permissões de acesso para diferentes endpoints da API.</li>
 * </ul>
 * 
 * @see SecurityFilter
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;


    /**
     * Configura o SecurityFilterChain para a aplicação.
     * 
     * Este método configura as regras de segurança da API, incluindo quais
     * endpoints são acessíveis publicamente e quais requerem autenticação.
     * 
     * @param httpSecurity a instância de HttpSecurity a ser configurada.
     * @return o objeto SecurityFilterChain configurado.
     * @throws Exception se ocorrer um erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                .requestMatchers(HttpMethod.GET, "/produtos/**").hasAnyRole("ADMIN", "USUARIO") // Consultar permitido para USER e ADMIN
                .requestMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")    // Inserir somente ADMIN
                .requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADMIN")  // Remover somente ADMIN
                .requestMatchers(HttpMethod.PUT, "/produtos/**").hasRole("ADMIN")     // Editar somente ADMIN
                .anyRequest().authenticated())


            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }


    /**
     * Configura o CorsConfigurationSource para a aplicação.
     * 
     * Este método define as regras de CORS, incluindo origens permitidas,
     * métodos e cabeçalhos que podem ser utilizados nas requisições.
     * 
     * @return uma instância de CorsConfigurationSource configurada.
     */
        @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*")); // Permite qualquer origem
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Métodos permitidos
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type")); // Headers permitidos
        configuration.setAllowCredentials(true); // Permite credenciais
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica CORS para todos os endpoints
        return source;
    }
    
    /**
     * Configura o AuthenticationManager para a aplicação.
     * 
     * Este método retorna o gerenciador de autenticação que será utilizado
     * para processar a autenticação dos usuários.
     * 
     * @param authenticationConfiguration a configuração de autenticação.
     * @return o objeto AuthenticationManager configurado.
     * @throws Exception se ocorrer um erro ao obter o gerenciador de autenticação.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


    /**
     * Configura o PasswordEncoder para a aplicação.
     * 
     * Este método retorna um codificador de senha baseado em BCrypt, que
     * será utilizado para codificar senhas de usuários.
     * 
     * @return uma instância de PasswordEncoder configurada.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}