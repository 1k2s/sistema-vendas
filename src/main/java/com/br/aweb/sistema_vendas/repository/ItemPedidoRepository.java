package com.br.aweb.sistema_vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.aweb.sistema_vendas.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    boolean existsByProdutoId(Long produtoId);

}
