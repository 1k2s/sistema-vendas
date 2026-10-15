package com.br.aweb.sistema_vendas.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="itens_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    // Evita recursão infinita no toString (Pedido -> itens -> pedido -> ...)
    @ToString.Exclude
    @NotNull(message="O pedido é obrigatório!")
    @ManyToOne
    @JoinColumn(name="pedido_id", nullable=false)
    private Pedido pedido;

    @NotNull(message="O produto é obrigatório!")
    @ManyToOne
    @JoinColumn(name="produto_id", nullable=false)
    private Produto produto;

    @NotNull(message="A quantidade é obrigatória!")
    @Positive(message="A quantidade deve ser maior que zero!")
    @Column(nullable=false)
    private Integer quantidade;

    @NotNull(message="O preço é obrigatório!")
    @Column(name="preco_unitario", nullable=false, precision=10, scale=2)
    private BigDecimal precoUnitario;

    // Construtor personalizado
    public ItemPedido(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }
}
