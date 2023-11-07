package com.mercadinho.estoque.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.mercadinho.estoque.dtos.inputs.ProdutoInput;
import com.mercadinho.estoque.utils.MyMvcMock;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ListarProdutoControllerTest {

	@Autowired
	private MyMvcMock mvc;
	private ProdutoInput produto;
	private String uriCadastro;
	private String uriListarTodos;

	@BeforeEach
	void antes() throws Exception {
		this.uriCadastro = "/produtos/cadastro";
		this.uriListarTodos = "/produtos/listar";

		this.produto = new ProdutoInput();
		this.produto.setNome("Produto1");
		this.produto.setPreco(12.00);
		this.produto.setQuantidade(1);
		mvc.created(uriCadastro, produto);

		this.produto.setNome("Produto2");
		mvc.created(uriCadastro, produto);
	}

	//
	@Test
	void quando_ListarProdutosRetornarSucesso() throws Exception {
		mvc.find(this.uriListarTodos);
		// result.andExpect(jsonPath("$.content[0].id").exists());
	}
}
