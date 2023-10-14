package com.mercadinho.estoque.dtos.inputs;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NomeProdutoInput {

	@NotBlank(message = "O nome é obrigatório")
	private String nome;
}
