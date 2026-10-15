package com.br.aweb.sistema_vendas.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.br.aweb.sistema_vendas.model.Usuario;
import com.br.aweb.sistema_vendas.model.UsuarioRole;
import com.br.aweb.sistema_vendas.repository.UsuarioRepository;

// Cria os usuários iniciais na primeira execução, já com a senha em hash BCrypt
@Component
public class CargaInicialUsuarios implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CargaInicialUsuarios(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            return;
        }

        usuarioRepository.save(new Usuario("Admin", "admin@email.com",
                passwordEncoder.encode("admin123"), UsuarioRole.ADMIN));
        usuarioRepository.save(new Usuario("User", "user@email.com",
                passwordEncoder.encode("user123"), UsuarioRole.PADRAO));
    }

}
