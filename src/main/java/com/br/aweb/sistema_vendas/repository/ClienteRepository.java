package com.br.aweb.sistema_vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.aweb.sistema_vendas.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
