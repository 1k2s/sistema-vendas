package com.br.aweb.sistema_vendas.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="clientes")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Cliente {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="O nome é obrigatório!")
    @Column(nullable=false, length=100)
    private String nome;

    @NotBlank(message="O email é obrigatório!")
    @Column(nullable=false, length=100, unique=true)
    @Email
    private String email;

    @NotBlank(message="O cpf é obrigatório!")
    @Column(nullable=false, length=11, unique=true)
    @CPF
    private String cpf;

    @NotBlank(message="O telefone é obrigatório!")
    @Column(nullable=false, length=20)
    private String telefone;

    @Valid
    @Embedded
    private Endereco endereco = new Endereco();

    // Lado inverso da relação 1:N (a chave estrangeira cliente_id fica na tabela pedidos)
    @ToString.Exclude
    @OneToMany(mappedBy="cliente")
    private List<Pedido> pedidos = new ArrayList<>();

}
