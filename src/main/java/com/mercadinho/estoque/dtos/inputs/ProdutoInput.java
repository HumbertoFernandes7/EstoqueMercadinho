package com.mercadinho.estoque.dtos.inputs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInput {

	@NotBlank(message = "O nome é obrigatório")
	private String nome;

	@NotNull(message = "A quantidade é obrigatório")
	private Integer quantidade;

	@NotNull(message = "O proço é obrigatório")
	private Double preco;
}
