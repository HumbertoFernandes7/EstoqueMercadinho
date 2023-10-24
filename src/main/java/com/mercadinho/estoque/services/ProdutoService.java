package com.mercadinho.estoque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadinho.estoque.dtos.inputs.ProdutoVendidoInput;
import com.mercadinho.estoque.dtos.inputs.ProdutosVendidosInput;
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

	public List<ProdutoEntity> buscarProdutoPeloNome(String nome) {
		List<ProdutoEntity> produtoEncontrado = produtoRepository.findByNomeContains(nome);
			return produtoEncontrado;
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
		if (quantidadeAtual >= 0) {
			produtoEncontrado.setQuantidade(quantidadeAtual);
			return produtoRepository.save(produtoEncontrado);
		} else {
			throw new BadRequestBussinessException("Quantidade inválida!");
		}
	}

	@Transactional
	public ProdutoEntity alterarProduto(ProdutoEntity produtoAtualizado) {
		ProdutoEntity produtoExistente = produtoRepository.findByNome(produtoAtualizado.getNome());
		if (produtoExistente == null || produtoExistente.getId() == produtoAtualizado.getId()) {
			return produtoRepository.save(produtoAtualizado);
		} else {
			throw new BadRequestBussinessException("Produto: " + produtoAtualizado.getNome() + " já cadastrado!");
		}
	}

	@Transactional
	public List<ProdutoVendidoInput> abaterVendaNoEstoque(ProdutosVendidosInput produtosVendidosInput) {

		List<ProdutoVendidoInput> listaDeProdutos = produtosVendidosInput.getProdutos();

		for (ProdutoVendidoInput produto : listaDeProdutos) {
			ProdutoEntity produtoEncontrado = buscarProdutoPorId(produto.getId());
			Integer quantidadeAntiga = produtoEncontrado.getQuantidade();
			quantidadeAntiga -= produto.getQuantidade();
			
			if (quantidadeAntiga >= 0) {
				produtoEncontrado.setQuantidade(quantidadeAntiga);
				produtoRepository.save(produtoEncontrado);
			} else {
				throw new BadRequestBussinessException(produtoEncontrado.getNome() + " sem estoque");
			}
			
		}
		return listaDeProdutos ;

	}

}
