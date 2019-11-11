package com.money.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.money.api.model.Categoria;
import com.money.api.model.Lancamento;
import com.money.api.model.Pessoa;
import com.money.api.repository.CategoriaRepository;
import com.money.api.repository.LancamentoRepository;
import com.money.api.repository.PessoaRepository;
import com.money.api.repository.filter.LancamentoFilter;
import com.money.api.service.exception.CategoriaInexistenteException;
import com.money.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired 
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	PessoaService pessoaService;
	
	public List<Lancamento> listarLancamentos() {
		return lancamentoRepository.findAll();
	}
	
	public Page<Lancamento> listarLancamentosCustomizado(LancamentoFilter lancamentoFilter, Pageable page) {
		 Page<Lancamento>  lancamentosFiltrados = lancamentoRepository.filtrarLancamento(lancamentoFilter, page);
		return lancamentosFiltrados;
	}

	public Lancamento listarLancamentoPorId(Long codigo) {
		Optional<Lancamento> lancamento = lancamentoRepository.findById(codigo);
		if(lancamento.isPresent()) {
			return lancamento.get();
		}else {
			throw new EmptyResultDataAccessException(1);
		}
	}
	/**
	 * 
	 * @param lancamento
	 * @return
	 */
	public Lancamento criaNovoLancamento(Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		Optional<Categoria> categoria = categoriaRepository.findById(lancamento.getCategoria().getCodigo());
		if(!pessoa.isPresent() || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		if(!categoria.isPresent()) {
			throw new CategoriaInexistenteException();
		}
		return lancamentoRepository.save(lancamento);
	}

	public void deletarLancamentoPorId(Long codigo) {
		lancamentoRepository.deleteById(codigo);
	}
	/**
	 * 
	 * @param lancamento
	 * @param codigo
	 * @return
	 */
	public Lancamento atualizarLancamento(Lancamento lancamento, Long codigo) {
		Optional<Lancamento> lancamentoSalvo = lancamentoRepository.findById(codigo);
		if(lancamentoSalvo.isPresent()){
			BeanUtils.copyProperties(lancamentoSalvo, lancamento,"codigo");
			return lancamentoRepository.save(lancamento);
		}else {
			throw new EmptyResultDataAccessException(1);
		}
	}
}
