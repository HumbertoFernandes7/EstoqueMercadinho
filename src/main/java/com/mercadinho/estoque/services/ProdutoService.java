package com.mercadinho.estoque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadinho.estoque.dtos.inputs.QuantidadeProdutoInput;
import com.mercadinho.estoque.entities.ProdutoEntity;
import com.mercadinho.estoque.exceptions.BadRequestBussinessException;
import com.mercadinho.estoque.exceptions.NotFoundBussinessException;
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

		ProdutoEntity produtoExistente = produtoRepository.findByNome(produtoCovetidoParaEntity.getNome());
		if (produtoExistente == null) {
			return produtoRepository.save(produtoCovetidoParaEntity);
		} else {
			throw new BadRequestBussinessException(
					"Produto: " + produtoCovetidoParaEntity.getNome() + " já cadastrado!");
		}
	}

	public ProdutoEntity buscarProdutoPorId(Long id) {
		return produtoRepository.findById(id)
				.orElseThrow(() -> new NotFoundBussinessException("Não foi encontrado o produto pelo id: " + id));
	}

	@Transactional
	public void deletarProduto(ProdutoEntity produtoEncontrado) {
		produtoRepository.delete(produtoEncontrado);
	}

	public ProdutoEntity buscarProdutoPeloNome(String nome) {
		ProdutoEntity produtoEncontrado = produtoRepository.findByNome(nome);
		if (produtoEncontrado != null) {
			return produtoEncontrado;
		} else {
			throw new BadRequestBussinessException("Produto: " + nome + " não cadastrado!");
		}
	}

	public List<ProdutoEntity> buscarProdutosPorEstoqueZerado() {
		Integer estoqueZerado = 0;
		List<ProdutoEntity> lista = produtoRepository.findByQuantidade(estoqueZerado);
		return lista;
	}

	@Transactional
	public ProdutoEntity adicionarQuantidadeProduto(QuantidadeProdutoInput quantidadeProdutoInput,
			ProdutoEntity produtoEncontrado) {
		Integer quantidadeAntiga = produtoEncontrado.getQuantidade();
		Integer quantidadeAtual = quantidadeAntiga += quantidadeProdutoInput.getQuantidade();
		produtoEncontrado.setQuantidade(quantidadeAtual);
		return produtoRepository.save(produtoEncontrado);
	}

	@Transactional
	public ProdutoEntity removerQuantidadeProduto(QuantidadeProdutoInput quantidadeProdutoInput,
			ProdutoEntity produtoEncontrado) {
		Integer quantidadeAntiga = produtoEncontrado.getQuantidade();
		Integer quantidadeAtual = quantidadeAntiga -= quantidadeProdutoInput.getQuantidade();
		produtoEncontrado.setQuantidade(quantidadeAtual);
		return produtoRepository.save(produtoEncontrado);
	}

}
