package dev.fernando.moneyapi.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import dev.fernando.moneyapi.event.RecursoCriadoEvent;
import dev.fernando.moneyapi.model.Lancamento;
import dev.fernando.moneyapi.service.LancamentoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("lancamentos")
public class LancamentoController {
    private final LancamentoService lancamentoService;
    private final ApplicationEventPublisher eventPublisher;
    public LancamentoController(final LancamentoService lancamentoService, final ApplicationEventPublisher eventPublisher) {
        this.lancamentoService = lancamentoService;
        this.eventPublisher = eventPublisher;
    }
    @GetMapping
    public ResponseEntity<List<Lancamento>> findAll() {
        return ResponseEntity.ok().body(this.lancamentoService.findAll());
    }
    
    @GetMapping("/{lancamentoId}")
    public ResponseEntity<Lancamento> findById(@Positive @PathVariable Long lancamentoId) {
        var lancamento = this.lancamentoService.findById(lancamentoId);
        return ResponseEntity.ok().body(lancamento);
    }

    @PostMapping
    public ResponseEntity<Lancamento> newLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        var insertedLancamento = this.lancamentoService.newLancamento(lancamento);
        this.eventPublisher.publishEvent(new RecursoCriadoEvent(insertedLancamento.getId(), response));
        return ResponseEntity.status(HttpStatus.CREATED).body(insertedLancamento);
    }
    @PutMapping("{id}")
    public ResponseEntity<Lancamento> put(@Positive @PathVariable Long id, @RequestBody @Valid Lancamento lancamento) {
        return ResponseEntity.ok(this.lancamentoService.update(id, lancamento));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@Positive @PathVariable Long id) {
        this.lancamentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
