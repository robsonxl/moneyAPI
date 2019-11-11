package com.money.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.money.api.model.Categoria;
import com.money.api.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository categoriaRepository;

	public List<Categoria> listarLancamentos() {
		return categoriaRepository.findAll();
	}
	
	/**
	 * 
	 * @param codigo
	 * @return
	 */
	public Categoria listarCategoriaPorId(Long codigo) {
		Optional<Categoria> categoria = categoriaRepository.findById(codigo);
		if(categoria.isPresent()) {
			return categoria.get();
		}else {
			throw new EmptyResultDataAccessException(1);
		}
	}
	/**
	 * 
	 * @param categoria
	 * @return
	 */
	public Categoria salvarNovaCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public void deletarPorId(Long codigo) {
		 categoriaRepository.deleteById(codigo);
	}
	/**
	 * 
	 * @param categoria
	 * @param codigo
	 * @return
	 */
	public Categoria atualizarCategoria(Categoria categoria, Long codigo) {
		Optional<Categoria> categoriaSalva = categoriaRepository.findById(codigo);
		if(categoriaSalva.isPresent()) {
			BeanUtils.copyProperties(categoriaSalva, categoria, "codigo");
			return categoriaRepository.save(categoria);
		}else {
			throw new EmptyResultDataAccessException(1);
		}
	}
}
