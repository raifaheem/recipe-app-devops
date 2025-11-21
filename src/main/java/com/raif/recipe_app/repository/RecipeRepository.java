package com.raif.recipe_app.repository;

import com.raif.recipe_app.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
