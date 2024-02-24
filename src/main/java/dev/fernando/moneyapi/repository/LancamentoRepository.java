package dev.fernando.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.fernando.moneyapi.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
