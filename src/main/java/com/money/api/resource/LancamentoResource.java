package com.money.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.money.api.event.RecursoCriadoEvent;
import com.money.api.exceptionHandler.MoneyExceptionHandler.Error;
import com.money.api.model.Lancamento;
import com.money.api.service.LancamentoService;
import com.money.api.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Lancamento> listar(){
		return lancamentoService.listarLancamentos();
	}
	
	@GetMapping("/{codigo}")
	public Lancamento listarPorId(@PathVariable Long codigo) {
		return lancamentoService.listarLancamentoPorId(codigo);
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> criarLancamento(@RequestBody @Valid Lancamento lancamento, HttpServletResponse response) {
		Lancamento novoLancamento = lancamentoService.criaNovoLancamento(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, novoLancamento.getCodigo()));
		return ResponseEntity.status(HttpStatus.OK).body(lancamento);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Lancamento> atualizarLancamento(@RequestBody @Valid Lancamento lancamento, @PathVariable Long codigo){
		Lancamento lancamentoAtualizado = lancamentoService.atualizarLancamento(lancamento, codigo);
		return ResponseEntity.ok().body(lancamentoAtualizado);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarLancamento(@PathVariable Long codigo) {
		lancamentoService.deletarLancamentoPorId(codigo);
	}
	
	/*
	 * @ExceptionHandler({PessoaInexistenteOuInativaException.class}) public
	 * ResponseEntity<Object>
	 * handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException
	 * ex){ String businessMsg =
	 * messageSource.getMessage("recurso.pessoa.inexistente.inativa", null,
	 * .getLocale()); String errorMsg = ex.toString(); List<Error> errors =
	 * Arrays.asList(new Error(businessMsg, errorMsg)); return
	 * ResponseEntity.badRequest().body(errors); }
	 */
}