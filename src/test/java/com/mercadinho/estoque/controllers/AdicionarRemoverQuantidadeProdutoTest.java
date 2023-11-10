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
import com.mercadinho.estoque.dtos.inputs.QuantidadeProdutoInput;
import com.mercadinho.estoque.utils.MyMvcMock;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdicionarRemoverQuantidadeProdutoTest {

	@Autowired
	private MyMvcMock mvc;
	private ProdutoInput produto;
	private QuantidadeProdutoInput quantidadeProduto;
	private String uriCadastro;
	private String uriAdicionaQuantidade;
	private String uriRemoveQuantidade;

	@BeforeEach
	void antes() throws Exception {
		this.uriCadastro = "/produtos/cadastro";
		this.uriAdicionaQuantidade = "/produtos/1/adicionarQuantidade";
		this.uriRemoveQuantidade = "/produtos/1/removerQuantidade";

		this.produto = new ProdutoInput();
		this.produto.setNome("Produto1");
		this.produto.setPreco(12.00);
		this.produto.setQuantidade(10);

		mvc.created(uriCadastro, produto);

		this.quantidadeProduto = new QuantidadeProdutoInput();
		this.quantidadeProduto.setQuantidade(2);

	}

	@Test
	void quando_AdicionarProdutoRetornarSucesso() throws Exception {
		ResultActions result = mvc.update(this.uriAdicionaQuantidade, this.quantidadeProduto);
		result.andExpect(jsonPath("$.quantidade").value(12));
	}

	@Test
	void quando_RemoverProdutoRetornarSucesso() throws Exception {
		ResultActions result = mvc.update(this.uriRemoveQuantidade, this.quantidadeProduto);
		result.andExpect(jsonPath("$.quantidade").value(8));
	}

	@Test
	void quando_RemoverProdutoQuantidadeMinimaRetornarErro() throws Exception {
		this.quantidadeProduto.setQuantidade(11);
		ResultActions result = mvc.updateWithBadRequest(this.uriRemoveQuantidade, this.quantidadeProduto);
		result.andExpect(jsonPath("$.message").value("O produto já possui a quantidade mínima!"));
	}
}
