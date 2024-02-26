package dev.fernando.moneyapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.fernando.moneyapi.model.Lancamento;
import dev.fernando.moneyapi.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {
    public Page<Lancamento> findAll(Pageable pageable);
}
