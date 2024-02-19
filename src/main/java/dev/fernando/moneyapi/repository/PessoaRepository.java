package dev.fernando.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.fernando.moneyapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
