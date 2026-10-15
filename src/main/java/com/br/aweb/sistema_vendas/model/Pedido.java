package com.br.aweb.sistema_vendas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="pedidos")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Pedido {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    // Muitos pedidos pertencem a um cliente: gera a chave estrangeira obrigatória cliente_id
    @NotNull(message="Cliente é obrigatório")
    @ManyToOne
    @JoinColumn(name="cliente_id", nullable=false)
    private Cliente cliente;

    @Column(nullable=false)
    private LocalDateTime dataPedido = LocalDateTime.now();

    @Column(nullable=false, precision=10, scale=2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    // STRING grava o nome da constante (ATIVO/CANCELADO) em vez do índice numérico
    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=10)
    private StatusPedido status = StatusPedido.ATIVO;

    // mappedBy: a chave estrangeira (pedido_id) fica em ItemPedido, que é o lado dono da relação.
    // cascade = ALL: operações feitas no pedido (salvar, atualizar, excluir) são propagadas para seus itens,
    // então basta salvar o pedido para que os itens também sejam salvos.
    // orphanRemoval = true: um item removido da lista "itens" fica órfão (sem pedido) e é excluído
    // automaticamente do banco.
    @OneToMany(mappedBy="pedido", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<ItemPedido> itens = new ArrayList<>();

    // Concorrência otimista: incrementado a cada atualização; uma transação com versão desatualizada falha
    @Version
    private Long version;

    // Construtor personalizado
    public Pedido(Cliente cliente) {
        this.cliente = cliente;
    }
}
