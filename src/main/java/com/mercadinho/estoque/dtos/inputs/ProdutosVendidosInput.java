package com.mercadinho.estoque.dtos.inputs;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutosVendidosInput {

	private List<ProdutoVendidoInput> produtos;

}
