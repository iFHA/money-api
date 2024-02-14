package dev.fernando.moneyapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.fernando.moneyapi.model.Category;
import dev.fernando.moneyapi.repository.CategoryRepository;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }
    public Optional<Category> findById(Long categoryId) {
        return this.categoryRepository.findById(categoryId);
    }
    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }
}
