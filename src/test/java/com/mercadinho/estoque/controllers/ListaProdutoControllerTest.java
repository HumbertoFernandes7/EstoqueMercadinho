package com.mercadinho.estoque.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class ListaProdutoControllerTest {
    
    @Autowired
    private MyMvcMock mvc;

    private String uri;
    private String uricadastro;
    private ProdutoInput produto;

    @BeforeEach
    void antes() throws Exception {
        this.uricadastro = "/produtos/cadastro";
        this.uri = "/produtos/listar";

        this.produto = new ProdutoInput();
        this.produto.setNome("Produto1");
        this.produto.setPreco(12.00);
        this.produto.setQuantidade(1);

        mvc.created(uricadastro, produto);
    }

    @Test
    void quando_ListarProdutoRetornarSucesso() throws Exception {
        ResultActions result = mvc.find(uri);
        result.andExpect(status().isOk());
    }
}
