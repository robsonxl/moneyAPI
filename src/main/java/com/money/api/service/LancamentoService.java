package com.money.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.money.api.model.Lancamento;
import com.money.api.model.Pessoa;
import com.money.api.repository.LancamentoRepository;
import com.money.api.repository.PessoaRepository;
import com.money.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired 
	private PessoaRepository pessoaRepository;
	
	@Autowired
	PessoaService pessoaService;
	
	public List<Lancamento> listarLancamentos() {
		return lancamentoRepository.findAll();
	}

	public Lancamento listarLancamentoPorId(Long codigo) {
		Optional<Lancamento> lancamento = lancamentoRepository.findById(codigo);
		if(lancamento.isPresent()) {
			return lancamento.get();
		}else {
			throw new EmptyResultDataAccessException(1);
		}
	}

	public Lancamento criaNovoLancamento(Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		if(!pessoa.isPresent() || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentoRepository.save(lancamento);
	}

	public void deletarLancamentoPorId(Long codigo) {
		lancamentoRepository.deleteById(codigo);
	}

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
