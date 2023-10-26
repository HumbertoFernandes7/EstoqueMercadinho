
package com.mercadinho.estoque.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mercadinho.estoque.converts.ProdutoConvert;
import com.mercadinho.estoque.dtos.inputs.ProdutoInput;
import com.mercadinho.estoque.dtos.inputs.ProdutoVendidoInput;
import com.mercadinho.estoque.dtos.inputs.ProdutosVendidosInput;
import com.mercadinho.estoque.dtos.inputs.QuantidadeProdutoInput;
import com.mercadinho.estoque.dtos.outputs.ProdutoOutput;
import com.mercadinho.estoque.dtos.outputs.ProdutoVendidoOutput;
import com.mercadinho.estoque.entities.ProdutoEntity;
import com.mercadinho.estoque.services.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin("*")
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
	public ProdutoOutput cadastrarProduto(@Valid @RequestBody ProdutoInput produtoInput) {
		ProdutoEntity produtoCovetidoParaEntity = produtoConvert.InputToNewEntity(produtoInput);
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

	@DeleteMapping("/{id}")
	public void deletarProduto(@PathVariable Long id) {
		ProdutoEntity produtoEncontrado = produtoService.buscarProdutoPorId(id);
		produtoService.deletarProduto(produtoEncontrado);
	}

	@GetMapping("/busca")
	public List<ProdutoOutput> buscarProdutoPeloNome(@RequestParam(required = true) String nomeProduto) {
		List<ProdutoEntity> produtoEncontrado = produtoService.buscarProdutoPeloNome(nomeProduto);
		List<ProdutoOutput> produtoConvertidoParaOutput = produtoConvert.listEntityToListOutput(produtoEncontrado);
		return produtoConvertidoParaOutput;
	}

	@GetMapping("/semEstoque")
	public List<ProdutoOutput> buscarProdutosPorEstoqueZerado() {
		List<ProdutoEntity> estoqueZerado = produtoService.buscarProdutosPorEstoqueZerado();
		List<ProdutoOutput> estoqueZeradoConvertidoParaOutput = produtoConvert.listEntityToListOutput(estoqueZerado);
		return estoqueZeradoConvertidoParaOutput;
	}

	@PutMapping("/{id}/adicionarQuantidade")
	public ProdutoOutput adicionarQuantidadeProduto(@PathVariable Long id,
			@RequestBody QuantidadeProdutoInput quantidadeProdutoInput) {
		ProdutoEntity produtoEncontrado = produtoService.buscarProdutoPorId(id);
		ProdutoEntity quantidadeAlterada = produtoService.adicionarQuantidadeProduto(quantidadeProdutoInput,
				produtoEncontrado);
		ProdutoOutput quantidadeConvertidaParaOutput = produtoConvert.EntityToOutput(quantidadeAlterada);
		return quantidadeConvertidaParaOutput;
	}

	@PutMapping("/{id}/removerQuantidade")
	public ProdutoOutput removerQuantidadeProduto(@PathVariable Long id,
			@RequestBody QuantidadeProdutoInput quantidadeProdutoInput) {
		ProdutoEntity produtoEncontrado = produtoService.buscarProdutoPorId(id);
		ProdutoEntity quantidadeAlterada = produtoService.removerQuantidadeProduto(quantidadeProdutoInput,
				produtoEncontrado);
		ProdutoOutput quantidadeConvertidaParaOutput = produtoConvert.EntityToOutput(quantidadeAlterada);
		return quantidadeConvertidaParaOutput;
	}

	@PutMapping("/{id}/alterar")
	public ProdutoOutput alterarProduto(@PathVariable Long id, @RequestBody @Valid ProdutoInput produtoInput) {
		ProdutoEntity produtoEncontrado = produtoService.buscarProdutoPorId(id);
		ProdutoEntity produtoAtualizado = produtoConvert.InputToUpdateEntity(produtoEncontrado, produtoInput);
		ProdutoEntity produtoAlterado = produtoService.alterarProduto(produtoAtualizado);
		ProdutoOutput produtoAtualizadoConveridoParaOutput = produtoConvert.EntityToOutput(produtoAlterado);
		return produtoAtualizadoConveridoParaOutput;
	}

	@PutMapping("/vender")
	public List<ProdutoVendidoOutput> abaterVendaNoEstoque(
			@RequestBody @Valid ProdutosVendidosInput produtosVendidosInput) {
		List<ProdutoVendidoInput> produtoAbatidos = produtoService.abaterVendaNoEstoque(produtosVendidosInput);
		List<ProdutoVendidoOutput> listaDeProdutosAbatidosConvertida = produtoConvert
				.listProdutoVendidoInputToListProdutoVendidoOutput(produtoAbatidos);
		return listaDeProdutosAbatidosConvertida;
	}
}
