package com.app.projetoGP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.projetoGP.model.AuthenticationDTO;
import com.app.projetoGP.model.LoginResponseDTO;
import com.app.projetoGP.model.RegisterDTO;
import com.app.projetoGP.model.Usuario;
import com.app.projetoGP.repository.UsuarioRepository;
import com.app.projetoGP.services.TokenService;

import jakarta.validation.Valid;

/**
 * Controlador responsável pela autenticação de usuários.
 * 
 * Este controlador fornece endpoints para login e registro de novos usuários.
 * O método de login autentica o usuário e gera um token JWT, enquanto o método
 * de registro cria um novo usuário no sistema com uma senha criptografada.
 */
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired 
    private UsuarioRepository repository;

    @Autowired
    TokenService tokenService;

    /**
     * Endpoint para autenticação de usuários.
     * 
     * Este método recebe as credenciais de login (login e senha) e, se forem válidas,
     * autentica o usuário e gera um token JWT.
     * 
     * @param data objeto contendo as credenciais de login (login e senha).
     * @return uma resposta HTTP com o token gerado, caso a autenticação seja bem-sucedida.
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);


        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    /**
     * Endpoint para registro de novos usuários.
     * 
     * Este método recebe os dados do novo usuário e o registra no sistema se o login
     * fornecido ainda não estiver em uso. A senha é criptografada antes de ser salva.
     * 
     * @param data objeto contendo os dados do usuário a ser registrado (login, senha e função).
     * @return uma resposta HTTP indicando se o registro foi bem-sucedido ou se o usuário já existe.
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().body("Usuário já existe.");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUser = new Usuario(data.login(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok("Usuário registrado com sucesso");
        
    }
    
}
