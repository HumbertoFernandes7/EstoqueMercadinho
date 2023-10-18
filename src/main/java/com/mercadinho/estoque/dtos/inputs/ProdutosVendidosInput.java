package com.mercadinho.estoque.dtos.inputs;

import java.util.List;

import com.mercadinho.estoque.exceptions.BadRequestBussinessException;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutosVendidosInput {

	@NotNull(message = "produtos é obrigatório")
	private List<ProdutoVendidoInput> produtos;

	
	
	public List<ProdutoVendidoInput>  erroSeNadaComoIdeaDeQuantidade() {
        if (produtos == null || produtos.isEmpty()) {
        	throw new BadRequestBussinessException("erro");
        }
        return null;
    }
}
	
