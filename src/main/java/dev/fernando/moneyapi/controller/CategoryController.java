package dev.fernando.moneyapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import dev.fernando.moneyapi.model.Category;
import dev.fernando.moneyapi.model.Pessoa;
import dev.fernando.moneyapi.service.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok().body(this.categoryService.findAll());
    }
    
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findById(@PathVariable Long categoryId) {
        var category = this.categoryService.findById(categoryId);
        return ResponseEntity.ok().body(category);
    }

    @PostMapping
    public ResponseEntity<Category> newCategory(@RequestBody Category category, HttpServletResponse response) {
        var insertedCategory = this.categoryService.save(category);
        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                                            .path("/{id}")
                                            .buildAndExpand(insertedCategory.getId())
                                            .toUri();
        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.status(HttpStatus.CREATED).body(insertedCategory);
    }
    @PutMapping("{id}")
    public ResponseEntity<Category> put(@PathVariable Long id, @RequestBody @Valid Category entity) {
        Category category = this.categoryService.findById(id);
        category.setName(entity.getName());
        this.categoryService.save(category);
        return ResponseEntity.ok(category);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
