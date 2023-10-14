package com.mercadinho.estoque.convets;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercadinho.estoque.dtos.inputs.ProdutoInput;
import com.mercadinho.estoque.dtos.outputs.ProdutoOutput;
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

	public ProdutoEntity InputEntity(ProdutoInput produtoInput) {
		return modelMapper.map(produtoInput, ProdutoEntity.class);
	}
}
