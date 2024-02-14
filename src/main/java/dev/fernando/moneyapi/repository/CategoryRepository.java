package dev.fernando.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.fernando.moneyapi.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
