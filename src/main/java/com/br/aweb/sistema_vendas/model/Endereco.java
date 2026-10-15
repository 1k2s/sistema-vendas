package com.br.aweb.sistema_vendas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Endereco {

    @NotBlank(message="O logradouro é obrigatório!")
    @Column(nullable=false, length=100)
    private String logradouro;

    @Column(length=10)
    private String numero;

    @Column(length=50)
    private String complemento;

    @NotBlank(message="O bairro é obrigatório!")
    @Column(nullable=false, length=50)
    private String bairro;

    @NotBlank(message="A cidade é obrigatória!")
    @Column(nullable=false, length=50)
    private String cidade;

    @NotBlank(message="A UF é obrigatória!")
    @Size(min=2, max=2, message="A UF deve ter 2 letras!")
    @Column(nullable=false, length=2, columnDefinition="char(2)")
    private String uf;

    @NotBlank(message="O CEP é obrigatório!")
    @Column(nullable=false, length=10)
    private String cep;
}
