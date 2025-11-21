package com.raif.recipe_app.controller;

import com.raif.recipe_app.model.Recipe;
import com.raif.recipe_app.repository.RecipeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeRepository repo;

    public RecipeController(RecipeRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Recipe> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Recipe create(@RequestBody Recipe recipe) {
        recipe.setId(null);
        recipe.setCreatedAt(LocalDateTime.now());
        return repo.save(recipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(@PathVariable Long id, @RequestBody Recipe updated) {
        return repo.findById(id)
                .map(existing -> {
                    existing.setTitle(updated.getTitle());
                    existing.setDescription(updated.getDescription());
                    existing.setIngredients(updated.getIngredients());
                    existing.setSteps(updated.getSteps());
                    return ResponseEntity.ok(repo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
