package com.money.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.money.api.model.Pessoa;
import com.money.api.repository.PessoaRepository;
import com.money.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class PessoaService {

	@Autowired
	PessoaRepository pessoaRepository;

	public Pessoa atualizarPessoa(Pessoa pessoa, Long codigo) {
		Optional<Pessoa> pessoaSalva = pessoaRepository.findById(codigo);
		if (pessoaSalva.isPresent()) {
			BeanUtils.copyProperties(pessoa, pessoaSalva.get(), "codigo");
			return pessoaRepository.save(pessoaSalva.get());
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

	public Pessoa atualizarAtributoAtivo(Long codigo, Boolean status) {
		Pessoa pessoa = buscarPessoaPeloCodigo(codigo);
		pessoa.setAtivo(status);
		return pessoaRepository.save(pessoa);
	}
	
	public Pessoa buscarPessoaPeloCodigo(Long codigo){
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
		if(pessoa.isPresent()){
			return pessoa.get();
		}else{
			throw new EmptyResultDataAccessException(1);
		}
	}
}
