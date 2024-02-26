package dev.fernando.moneyapi.repository.lancamento;

import java.util.List;

import dev.fernando.moneyapi.model.Lancamento;
import dev.fernando.moneyapi.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
    List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
