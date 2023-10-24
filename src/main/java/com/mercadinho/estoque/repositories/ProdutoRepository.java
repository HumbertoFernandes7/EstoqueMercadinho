package com.mercadinho.estoque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadinho.estoque.entities.ProdutoEntity;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

	ProdutoEntity findByNome(String nome);
	
	List<ProdutoEntity> findByNomeContains(String nome);

	List<ProdutoEntity> findByQuantidade(Integer estoqueZerado);

}
