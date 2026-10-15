package com.br.aweb.sistema_vendas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.aweb.sistema_vendas.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmailIgnoreCase(String email);

}
