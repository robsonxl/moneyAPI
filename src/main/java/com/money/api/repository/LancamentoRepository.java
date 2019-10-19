package com.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.money.api.model.Lancamento;

public interface LancamentoRepository  extends JpaRepository<Lancamento, Long>{

}
