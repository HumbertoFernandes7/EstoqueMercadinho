package com.mercadinho.estoque.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mercadinho.estoque.convets.ProdutoConvert;
import com.mercadinho.estoque.dtos.inputs.ProdutoInput;
import com.mercadinho.estoque.dtos.outputs.ProdutoOutput;
import com.mercadinho.estoque.entities.ProdutoEntity;
import com.mercadinho.estoque.services.ProdutoService;

import jakarta.validation.Valid;

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
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/cadastro")
	public ProdutoOutput cadastrarProduto (@Valid @RequestBody ProdutoInput produtoInput){
		
		ProdutoEntity produtoCovetidoParaEntity = produtoConvert.InputEntity(produtoInput);
		
		ProdutoEntity produtoCadastrado = produtoService.cadastrarProduto(produtoCovetidoParaEntity);
		
		ProdutoOutput produtoConvertidoParaOutput = produtoConvert.EntityToOutput(produtoCadastrado);
		
		return produtoConvertidoParaOutput;
	}
	
	@GetMapping("/{id}")
	public ProdutoOutput buscarProdutoPorId(@PathVariable Long id) {
		ProdutoEntity produtoEncontrado = produtoService.buscarProdutoPorId(id);
		
		ProdutoOutput produtoConvertidoParaOutput = produtoConvert.EntityToOutput(produtoEncontrado);
		
		return produtoConvertidoParaOutput;
	}
}


