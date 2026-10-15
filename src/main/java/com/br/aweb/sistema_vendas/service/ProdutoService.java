package com.br.aweb.sistema_vendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.aweb.sistema_vendas.model.Produto;
import com.br.aweb.sistema_vendas.repository.ItemPedidoRepository;
import com.br.aweb.sistema_vendas.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service 
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public ProdutoService(ProdutoRepository produtoRepository, ItemPedidoRepository itemPedidoRepository) {
        this.produtoRepository = produtoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    // CREATE
    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    // READ
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    // UPDATE
    @Transactional
    public Produto atualizar(Long id, Produto produtoAtualizado) {

        var optionalProduto = buscarPorId(id);

        if (!optionalProduto.isPresent()) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }

        var produtoExistente = optionalProduto.get();

        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setDescricao(produtoAtualizado.getDescricao());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setQuantidadeEmEstoque(produtoAtualizado.getQuantidadeEmEstoque());

        var produtoSalvo = produtoRepository.save(produtoExistente);

        return produtoSalvo;

    }

    // DELETE
    @Transactional
    public void excluir(Long id) {

        var optionalProduto = buscarPorId(id);
        
        if (!optionalProduto.isPresent()) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }

        // A chave estrangeira itens_pedido.produto_id impede excluir produto usado em pedidos
        if (itemPedidoRepository.existsByProdutoId(id)) {
            throw new IllegalStateException("Não é possível excluir o produto, pois ele está em pedidos cadastrados.");
        }

        produtoRepository.deleteById(id);
    }

}
