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
public class AlterarProdutoControllerTest {

	@Autowired
	private MyMvcMock mvc;
	private ProdutoInput produto;
	private String uriCadastro;
	private String uriAlterar;

	@BeforeEach
	void antes() throws Exception {
		this.uriCadastro = "/produtos/cadastro";
		this.uriAlterar = "/produtos/1/alterar";

		this.produto = new ProdutoInput();
		this.produto.setNome("Produto1");
		this.produto.setPreco(12.00);
		this.produto.setQuantidade(1);

		mvc.created(uriCadastro, produto);
	}

	// nome
	@Test
	void quando_AlterarProdutoRetornarSucesso() throws Exception {
		this.produto.setNome("Produto2");
		this.produto.setPreco(12.00);
		this.produto.setQuantidade(1);
		mvc.update(this.uriAlterar, this.produto);
	}

	@Test
	void quando_AlterarProdutoRetornarNomeObrigatorio() throws Exception {
		this.produto.setNome("");
		mvc.updateWithBadRequest(this.uriAlterar, this.produto);
	}

	@Test
	void quando_AlterarProdutoRetornarNomeNulo() throws Exception {
		this.produto.setNome(null);
		mvc.updateWithBadRequest(this.uriAlterar, this.produto);
	}

	@Test
	void quando_AlterarProdutoRetornarNomeExistente() throws Exception {
		this.produto.setNome("Produto2");
		mvc.created(this.uriCadastro, this.produto);
		ResultActions result = mvc.updateWithBadRequest(this.uriAlterar, this.produto);
		result.andExpect(jsonPath("[?($.message == 'Produto: Produto2 já cadastrado!')]").exists());
	}

	// preço

	@Test
	void quando_AlterarProdutoRetornarPrecoObrigatorio() throws Exception {
		this.produto.setNome("");
		mvc.updateWithBadRequest(this.uriAlterar, this.produto);
	}

	@Test
	void quando_AlterarProdutoRetornarPrecoNulo() throws Exception {
		this.produto.setPreco(null);
		mvc.updateWithBadRequest(this.uriAlterar, this.produto);
	}

	// quantidade

	@Test
	void quando_AlterarProdutoRetornarQuantidadeNulo() throws Exception {
		this.produto.setQuantidade(null);
		mvc.updateWithBadRequest(this.uriAlterar, this.produto);
	}

}
