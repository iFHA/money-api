package dev.fernando.moneyapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.fernando.moneyapi.exception.ErroApi;
import dev.fernando.moneyapi.exception.NotFoundException;
import dev.fernando.moneyapi.exception.PessoaInativaException;
import dev.fernando.moneyapi.model.Lancamento;
import dev.fernando.moneyapi.model.Pessoa;
import dev.fernando.moneyapi.repository.LancamentoRepository;
import dev.fernando.moneyapi.repository.PessoaRepository;
import dev.fernando.moneyapi.repository.filter.LancamentoFilter;

@Service
public class LancamentoService {
    private final PessoaRepository pessoaRepository;
    private final LancamentoRepository lancamentoRepository;
    public LancamentoService(
        final LancamentoRepository lancamentoRepository,
        final PessoaRepository pessoaRepository
    ) {
        this.lancamentoRepository = lancamentoRepository;
        this.pessoaRepository = pessoaRepository;
    }
    public List<Lancamento> findAll(LancamentoFilter filtro) {
        return this.lancamentoRepository.filtrar(filtro);
    }
    public Lancamento findById(Long lancamentoId) {
        return this.lancamentoRepository.findById(lancamentoId).orElseThrow(() -> new NotFoundException("Lancamento de id = %d não encontrado!".formatted(lancamentoId)));
    }
    public Lancamento newLancamento(Lancamento lancamento) {
        this.validarPessoaInativa(lancamento.getPessoa().getId());
        return this.lancamentoRepository.save(lancamento);
    }
    public Lancamento update(Long id, Lancamento lancamento) {
        Lancamento lancamentoDB = this.findById(id);
        this.validarPessoaInativa(lancamento.getPessoa().getId());
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
    private void validarPessoaInativa(Long pessoaId) {
        Optional<Pessoa> pessoa = this.pessoaRepository.findById(pessoaId);
        if(pessoa.isPresent()){
            lancaExcecaoSePessoaInativa(pessoa.get());
        }
    }
    private void lancaExcecaoSePessoaInativa(Pessoa pessoa) {
        if(!pessoa.isAtivo()) {
            throw new PessoaInativaException("Pessoa de id = %d está inativa!".formatted(pessoa.getId()));
        }
    }
}
