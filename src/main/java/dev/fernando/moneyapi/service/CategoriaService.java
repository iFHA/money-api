package dev.fernando.moneyapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.fernando.moneyapi.exception.NotFoundException;
import dev.fernando.moneyapi.model.Categoria;
import dev.fernando.moneyapi.repository.CategoriaRepository;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    public CategoriaService(final CategoriaRepository categoryRepository) {
        this.categoriaRepository = categoryRepository;
    }
    public List<Categoria> findAll() {
        return this.categoriaRepository.findAll();
    }
    public Categoria findById(Long categoryId) {
        return this.categoriaRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Categoria de id = %d não encontrada!".formatted(categoryId)));
    }
    public Categoria save(Categoria category) {
        return this.categoriaRepository.save(category);
    }
    public void deleteById(Long id) {
        var category = this.findById(id);
        this.categoriaRepository.delete(category);
    }
}
