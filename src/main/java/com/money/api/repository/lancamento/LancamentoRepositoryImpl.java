package com.money.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.money.api.model.Lancamento;
import com.money.api.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{
	
	@PersistenceContext
	private EntityManager entityManger;
	
 
	@Override
	public Page<Lancamento> filtrarLancamento(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = entityManger.getCriteriaBuilder();
		
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] restricoes = criarRestricoes(lancamentoFilter,builder,root);
		
		criteria.where(restricoes);
		
		TypedQuery<Lancamento> query = entityManger.createQuery(criteria);
		
		adicionarPaginacao(query, pageable);
		
		Long total = totalPaginacao(lancamentoFilter);
		
		List<Lancamento> resultadoPesquisa = query.getResultList();
		
		return new PageImpl<Lancamento>(resultadoPesquisa, pageable, total);
	}

 
	private Long totalPaginacao(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = entityManger.getCriteriaBuilder();
		
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		
		criteria.where(predicates);
		criteria.select(builder.count(root));
		
		Long total = entityManger.createQuery(criteria).getSingleResult();
		return total;
	}


	private void adicionarPaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistro);
		query.setMaxResults(totalRegistroPorPagina);
	}


	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(lancamentoFilter!=null){
			if (lancamentoFilter.getDescricao().isPresent()) {
				predicates.add(builder.like(
						builder.lower(
								root.get("descricao")),"%"+ lancamentoFilter.getDescricao().toString().toLowerCase() +"%"));
			}
			if(lancamentoFilter.getDataVencimentoDe().isPresent()) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimento"), lancamentoFilter.getDataVencimentoDe().get()));
			}
			if(lancamentoFilter.getDataVencimentoAte().isPresent()) {
				predicates.add( builder.lessThanOrEqualTo(root.get("dataVencimento"), lancamentoFilter.getDataVencimentoAte().get()));
			}
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	

}
