package com.example.jubtibe.repository;

import com.example.jubtibe.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository <Recipe, Long> {
    // 작성자 박성민
    @Query(value = "SELECT DISTINCT r from Recipe r order by r.recipeLike.size desc")
    List<Recipe> findAllByOrderByRecipeLikeDesc();
}
