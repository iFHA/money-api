package dev.fernando.moneyapi.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.fernando.moneyapi.model.Lancamento;
import dev.fernando.moneyapi.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
    Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
}
