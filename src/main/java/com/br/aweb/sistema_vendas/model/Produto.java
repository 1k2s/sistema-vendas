package com.br.aweb.sistema_vendas.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity 
@Table(name="produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Produto {

    @Id 
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="O nome é obrigatório!")
    @Column(nullable=false, length=100)
    private String nome;

    @NotBlank(message="A descrição é obrigatória!")
    @Column(nullable=false, length=255)
    private String descricao;

    @NotNull(message="O preço é obrigatório!")
    @Column(nullable=false, precision=10, scale=2)
    @Positive(message="O valor deve ser maior que zero!")
    private BigDecimal preco;

    // @NotBlank(message="A quantidade é obrigatória!")
    @NotNull(message="A quantidade é obrigatória!")
    @Column(nullable=false, name="quantidade_em_estoque")
    @PositiveOrZero(message="O valor deve ser maior ou igual a zero!")
    private Integer quantidadeEmEstoque;

    @Version
    private Long version;

}
