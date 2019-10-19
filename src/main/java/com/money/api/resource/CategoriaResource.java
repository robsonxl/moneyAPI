package com.money.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.money.api.model.Categoria;
import com.money.api.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Categoria>listar(){
		return categoriaService.listarLancamentos();
	}
	
	@GetMapping("/{codigo}")
	public Categoria buscarPorIdCodigo(@PathVariable Long codigo) {
		return categoriaService.listarCategoriaPorId(codigo);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping
	public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		 Categoria novaCategoria = categoriaService.salvarNovaCategoria(categoria);
		 publisher.publishEvent(new RecursoCriadoEvent(this, response, novaCategoria.getCodigo())); 
		 return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Categoria> atualizarCategoria(@RequestBody @Valid Categoria categoria, @PathVariable Long codigo){
		Categoria categoriaAtualizada = categoriaService.atualizarCategoria(categoria, codigo);
		return ResponseEntity.ok().body(categoriaAtualizada);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarCategoria(@PathVariable Long codigo){
		categoriaService.deletarPorId(codigo);
	}
	
}