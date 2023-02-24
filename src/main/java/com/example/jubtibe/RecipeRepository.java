package com.example.jubtibe;

import com.example.jubtibe.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository <Recipe, Long> {
    // 작성자 박성민
    List<Recipe> findAllByOrderByCreatedAtDesc();
}
