package com.money.api.repository.filter;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;

public class LancamentoFilter {
	
	private Optional<String> descricao = Optional.empty();;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Optional<LocalDate> dataVencimentoDe = Optional.empty();;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Optional<LocalDate> dataVencimentoAte = Optional.empty();;
	
	public Optional<String> getDescricao() {
		return descricao;
	}
	public void setDescricao(Optional<String> descricao) {
		this.descricao = descricao;
	}
	public Optional<LocalDate> getDataVencimentoDe() {
		return dataVencimentoDe;
	}
	public void setDataVencimentoDe(Optional<LocalDate> dataVencimentoDe) {
		this.dataVencimentoDe = dataVencimentoDe;
	}
	public Optional<LocalDate> getDataVencimentoAte() {
		return dataVencimentoAte;
	}
	public void setDataVencimentoAte(Optional<LocalDate> dataVencimentoAte) {
		this.dataVencimentoAte = dataVencimentoAte;
	}
	
	 
	

}
