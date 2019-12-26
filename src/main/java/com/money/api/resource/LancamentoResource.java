package com.money.api.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.money.api.event.RecursoCriadoEvent;
import com.money.api.model.Lancamento;
import com.money.api.repository.filter.LancamentoFilter;
import com.money.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Lancamento listarPorId(@PathVariable Long codigo) {
		return lancamentoService.listarLancamentoPorId(codigo);
	}
	
	@GetMapping 
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	  public Page<Lancamento>listarLancamentos(LancamentoFilter lancamentoFilter, Pageable page){ 
		  return lancamentoService.listarLancamentosCustomizado(lancamentoFilter, page); 
	}
	 
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> criarLancamento(@RequestBody @Valid Lancamento lancamento,
			HttpServletResponse response) {
		Lancamento novoLancamento = lancamentoService.criaNovoLancamento(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, novoLancamento.getCodigo()));
		return ResponseEntity.status(HttpStatus.OK).body(lancamento);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Lancamento> atualizarLancamento(@RequestBody @Valid Lancamento lancamento,
			@PathVariable Long codigo) {
		Lancamento lancamentoAtualizado = lancamentoService.atualizarLancamento(lancamento, codigo);
		return ResponseEntity.ok().body(lancamentoAtualizado);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void deletarLancamento(@PathVariable Long codigo) {
		lancamentoService.deletarLancamentoPorId(codigo);
	}
}