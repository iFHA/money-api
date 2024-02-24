package dev.fernando.moneyapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.fernando.moneyapi.exception.NotFoundException;
import dev.fernando.moneyapi.model.Lancamento;
import dev.fernando.moneyapi.repository.LancamentoRepository;

@Service
public class LancamentoService {
    private final LancamentoRepository lancamentoRepository;
    public LancamentoService(final LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }
    public List<Lancamento> findAll() {
        return this.lancamentoRepository.findAll();
    }
    public Lancamento findById(Long lancamentoId) {
        return this.lancamentoRepository.findById(lancamentoId).orElseThrow(() -> new NotFoundException("Lancamento de id = %d não encontrado!".formatted(lancamentoId)));
    }
    public Lancamento newLancamento(Lancamento lancamento) {
        return this.lancamentoRepository.save(lancamento);
    }
    public Lancamento update(Long id, Lancamento lancamento) {
        Lancamento lancamentoDB = this.findById(id);
        lancamentoDB.setDescricao(lancamento.getDescricao());
        lancamentoDB.setDataVencimento(lancamento.getDataVencimento());
        lancamentoDB.setDataPagamento(lancamento.getDataPagamento());
        lancamentoDB.setValor(lancamento.getValor());
        lancamentoDB.setObservacao(lancamento.getObservacao());
        lancamentoDB.setTipo(lancamento.getTipo());
        lancamentoDB.setCategoria(lancamento.getCategoria());
        lancamentoDB.setPessoa(lancamento.getPessoa());
        return this.lancamentoRepository.save(lancamentoDB);
    }
    public void deleteById(Long id) {
        var lancamento = this.findById(id);
        this.lancamentoRepository.delete(lancamento);
    }
}
