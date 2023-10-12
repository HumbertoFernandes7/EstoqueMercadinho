package com.mercadinho.estoque.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadinho.estoque.convets.ProdutoConvert;
import com.mercadinho.estoque.dtos.outputs.ProdutoOutput;
import com.mercadinho.estoque.entities.ProdutoEntity;
import com.mercadinho.estoque.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoConvert produtoConvert;
	
	@GetMapping("/listar")
	public List<ProdutoOutput> ListarProdutos() {
		
		List<ProdutoEntity> produtosEncontrados = produtoService.buscarTodosProdutos();
	
		List<ProdutoOutput> produtosConvertidos = produtoConvert.listEntityToListOutput(produtosEncontrados);
		
		return produtosConvertidos;
		
	}
}
