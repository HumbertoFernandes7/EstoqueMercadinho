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
public class BuscarProdutoControllerTest {

	@Autowired
	private MyMvcMock mvc;
	private ProdutoInput produto;
	private String uriCadastro;
	private String uriBuscaPorId;
	private String uriBuscaPeloNome;
	private String uriBuscaSemEstoque;

	@BeforeEach
	void antes() throws Exception {
		this.uriCadastro = "/produtos/cadastro";
		this.uriBuscaPorId = "/produtos/1";
		this.uriBuscaPeloNome = "/produtos/busca?&nomeProduto=Pro";
		this.uriBuscaSemEstoque = "/produtos/semEstoque";

		this.produto = new ProdutoInput();
		this.produto.setNome("Produto1");
		this.produto.setPreco(12.00);
		this.produto.setQuantidade(1);

		mvc.created(uriCadastro, produto);

		this.produto.setNome("Produto2");
		mvc.created(uriCadastro, produto);
	}

	// busca por Id
	@Test
	void quando_BuscarProdutoPorIdNaoCadastradoRetornarErro() throws Exception {
		ResultActions result = mvc.findWithNotFound("/produtos/3");
		result.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.message").value("Não foi encontrado o produto pelo id: 3"));
	}

	@Test
	void quando_BuscarProdutoPorIdRetornarSucesso() throws Exception {
		ResultActions result = mvc.find(this.uriBuscaPorId);
		result.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.nome").value("Produto1"))
			.andExpect(jsonPath("$.preco").value(12.00))
			.andExpect(jsonPath("$.quantidade").value(1));
	}

	// busca por nome
	@Test
	void quando_BuscarProdutoPeloNomeRetornarSucesso() throws Exception {
		ResultActions result = mvc.find(this.uriBuscaPeloNome);
		result.andExpect(jsonPath("$[0].id").value(1))
			.andExpect(jsonPath("$[0].nome").value("Produto1"))
			.andExpect(jsonPath("$[0].preco").value(12.00))
			.andExpect(jsonPath("$[0].quantidade").value(1))
			.andExpect(jsonPath("$[1].id").value(2))
			.andExpect(jsonPath("$[1].nome").value("Produto2"))
			.andExpect(jsonPath("$[1].preco").value(12.00))
			.andExpect(jsonPath("$[1].quantidade").value(1));
	}
	
	// busca produto sem estoque
	@Test
	void quando_BuscarProdutoSemEstoqueRetornarSucesso() throws Exception {
		this.produto.setNome("Produto3");
		this.produto.setPreco(12.00);
		this.produto.setQuantidade(0);
		mvc.created(this.uriCadastro, this.produto);
		ResultActions result = mvc.find(this.uriBuscaSemEstoque);
		result.andExpect(jsonPath("$[0].id").value(3))
			.andExpect(jsonPath("$[0].nome").value("Produto3"))
			.andExpect(jsonPath("$[0].preco").value(12.00))
			.andExpect(jsonPath("$[0].quantidade").value(0));
	}
}
