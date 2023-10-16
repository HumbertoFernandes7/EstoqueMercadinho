package com.mercadinho.estoque.dtos.inputs;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutosVendidosInput {

	@NotBlank(message =  "Produtos é obrigátorio")
	private List<ProdutoVendidoInput> produtos;

}
