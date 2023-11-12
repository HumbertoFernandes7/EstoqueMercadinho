package com.mercadinho.estoque.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

import com.mercadinho.estoque.dtos.inputs.ProdutoInput;
import com.mercadinho.estoque.utils.MyMvcMock;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class CadastraProdutoControllerTest {

	@Autowired
	private MyMvcMock mvc;

	private String uri;
	private ProdutoInput produto;

	@BeforeEach
	void antes() throws Exception {
		this.uri = "/produtos/cadastro";

		this.produto = new ProdutoInput();
		this.produto.setNome("Produto1");
		this.produto.setPreco(12.00);
		this.produto.setQuantidade(1);

	}

	// nome
	@Test
	void quando_cadastrarProdutoNomeNuloRetornarErro() throws Exception {
		this.produto.setNome(null);
		mvc.createdWithBadRequest(this.uri, this.produto);
	}

	@Test
	void quando_cadastrarProdutoSemNomeRetornarErro() throws Exception {
		this.produto.setNome("");
		mvc.createdWithBadRequest(this.uri, this.produto);
	}

	@Test
	void quando_cadastrarProdutoNomeDuplicadoRetornarErro() throws Exception {
		mvc.created(this.uri, this.produto);
		ResultActions result = mvc.createdWithBadRequest(this.uri, this.produto);
		result.andExpect(jsonPath("[?($.message == 'Produto: Produto1 já cadastrado!')]").exists());
	}

	// preco
	@Test
	void quando_cadastrarProdutoPrecoNuloRetornarErro() throws Exception {
		this.produto.setPreco(null);
		mvc.createdWithBadRequest(this.uri, this.produto);
	}

	// quantidade
	@Test
	void quando_cadastrarProdutoQuantidadeNuloRetornarErro() throws Exception {
		this.produto.setQuantidade(null);
		mvc.createdWithBadRequest(this.uri, this.produto);
	}
	
	@Test
	void quando_cadastrarProdutoQuantidadeMenorQueZeroRetornarErro() throws Exception{
		this.produto.setQuantidade(-1);
		ResultActions result = mvc.createdWithBadRequest(this.uri, this.produto);
		result.andExpect(jsonPath("[?($.message == 'Quantidade do produto não pode ser menor que 0')]").exists());
	}

	// sucesso
	@Test
	void quando_cadastrarProdutoRetornarSucesso() throws Exception {
		mvc.created(this.uri, this.produto);
	}
	

}
