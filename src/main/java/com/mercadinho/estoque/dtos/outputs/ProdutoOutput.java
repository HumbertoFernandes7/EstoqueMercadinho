package com.mercadinho.estoque.dtos.outputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoOutput {

	private Long id;

	private String nome;

	private Double preco;

	private Integer quantidade;
}
