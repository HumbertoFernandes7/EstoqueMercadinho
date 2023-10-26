package com.mercadinho.estoque.converts;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercadinho.estoque.dtos.inputs.ProdutoInput;
import com.mercadinho.estoque.dtos.inputs.ProdutoVendidoInput;
import com.mercadinho.estoque.dtos.outputs.ProdutoOutput;
import com.mercadinho.estoque.dtos.outputs.ProdutoVendidoOutput;
import com.mercadinho.estoque.entities.ProdutoEntity;

@Component
public class ProdutoConvert {

	@Autowired
	private ModelMapper modelMapper;

	public ProdutoOutput EntityToOutput(ProdutoEntity produtoEntity) {
		return modelMapper.map(produtoEntity, ProdutoOutput.class);
	}

	public List<ProdutoOutput> listEntityToListOutput(List<ProdutoEntity> produtosEncontrados) {
		return produtosEncontrados.stream().map(a -> this.EntityToOutput(a)).collect(Collectors.toList());
	}

	public ProdutoEntity InputToNewEntity(ProdutoInput produtoInput) {
		return modelMapper.map(produtoInput, ProdutoEntity.class);
	}

	public ProdutoEntity InputToUpdateEntity(ProdutoEntity produtoEncontrado, ProdutoInput produtoInput) {
		ProdutoEntity produtoAtualizado = modelMapper.map(produtoInput, ProdutoEntity.class);
		produtoAtualizado.setId(produtoEncontrado.getId());
		return produtoAtualizado;
	}

	public ProdutoVendidoOutput ProdutoVendidoInputToProdutoVendidoOutput(ProdutoVendidoInput produtoAbatidos) {
		return modelMapper.map(produtoAbatidos, ProdutoVendidoOutput.class);
	}

	public List<ProdutoVendidoOutput> listProdutoVendidoInputToListProdutoVendidoOutput(
			List<ProdutoVendidoInput> produtoAbatidos) {
		return produtoAbatidos.stream().map(a -> this.ProdutoVendidoInputToProdutoVendidoOutput(a))
				.collect(Collectors.toList());

	}
}
