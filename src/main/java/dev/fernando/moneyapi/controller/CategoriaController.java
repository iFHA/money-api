package dev.fernando.moneyapi.controller;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.fernando.moneyapi.event.RecursoCriadoEvent;
import dev.fernando.moneyapi.model.Categoria;
import dev.fernando.moneyapi.service.CategoriaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;
    private final ApplicationEventPublisher eventPublisher;
    public CategoriaController(
        final CategoriaService categoryService,
        final ApplicationEventPublisher eventPublisher) {
        this.categoriaService = categoryService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> findAll() {
        return ResponseEntity.ok().body(this.categoriaService.findAll());
    }
    
    @GetMapping("/{categoryId}")
    public ResponseEntity<Categoria> findById(@PathVariable Long categoryId) {
        var category = this.categoriaService.findById(categoryId);
        return ResponseEntity.ok().body(category);
    }

    @PostMapping
    public ResponseEntity<Categoria> newCategory(@RequestBody Categoria category, HttpServletResponse response) {
        var insertedCategory = this.categoriaService.save(category);
        this.eventPublisher.publishEvent(new RecursoCriadoEvent(insertedCategory.getId(), response));
        return ResponseEntity.status(HttpStatus.CREATED).body(insertedCategory);
    }
    @PutMapping("{id}")
    public ResponseEntity<Categoria> put(@PathVariable Long id, @RequestBody @Valid Categoria entity) {
        Categoria category = this.categoriaService.findById(id);
        category.setNome(entity.getNome());
        this.categoriaService.save(category);
        return ResponseEntity.ok(category);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
