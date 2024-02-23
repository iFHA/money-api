package dev.fernando.moneyapi.controller;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.fernando.moneyapi.event.RecursoCriadoEvent;
import dev.fernando.moneyapi.model.Pessoa;
import dev.fernando.moneyapi.service.PessoaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@RestController
@RequestMapping("pessoas")
public class PessoaController {
    private final PessoaService pessoaService;
    private final ApplicationEventPublisher eventPublisher;
    public PessoaController(
        final PessoaService pessoaService, 
        final ApplicationEventPublisher eventPublisher) {
        this.pessoaService = pessoaService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> findAll() {
        return ResponseEntity.ok().body(this.pessoaService.findAll());
    }
    
    @GetMapping("/{pessoaId}")
    public ResponseEntity<Pessoa> findById(@PathVariable Long pessoaId) {
        var pessoa = this.pessoaService.findById(pessoaId);
        return ResponseEntity.ok().body(pessoa);
    }

    @PostMapping
    public ResponseEntity<Pessoa> newPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        var insertedPessoa = this.pessoaService.newPessoa(pessoa);
        this.eventPublisher.publishEvent(new RecursoCriadoEvent(insertedPessoa.getId(), response));
        return ResponseEntity.status(HttpStatus.CREATED).body(insertedPessoa);
    }
    @PutMapping("{id}")
    public ResponseEntity<Pessoa> put(@Positive @PathVariable Long id, @RequestBody @Valid Pessoa pessoa) {
        return ResponseEntity.ok(this.pessoaService.update(id, pessoa));
    }
    @PatchMapping("{id}/ativo")
    public ResponseEntity<Void> updateAtivo(@Positive @PathVariable Long id, @NotNull @RequestBody Boolean ativo) {
        this.pessoaService.updateAtivo(id, ativo);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@Positive @PathVariable Long id) {
        this.pessoaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
