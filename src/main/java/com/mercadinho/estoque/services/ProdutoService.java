package com.mercadinho.estoque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadinho.estoque.entities.ProdutoEntity;
import com.mercadinho.estoque.repositories.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public List<ProdutoEntity> buscarTodosProdutos() {
		return produtoRepository.findAll();
	}

	@Transactional
	public ProdutoEntity cadastrarProduto(ProdutoEntity produtoCovetidoParaEntity) {
		return produtoRepository.save(produtoCovetidoParaEntity);
	}

	public ProdutoEntity buscarProdutoPorId(Long id) {
		return produtoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Não foi encontrado o produto pelo id: " + id));
	}

	public void deletarProduto(ProdutoEntity produtoEncontrado) {
		produtoRepository.delete(produtoEncontrado);
	}

}
