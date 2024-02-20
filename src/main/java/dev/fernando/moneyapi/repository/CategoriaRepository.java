package dev.fernando.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.fernando.moneyapi.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}
