package com.example.jubtibe.repository;

import com.example.jubtibe.domain.like.image.entity.Images;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Images, Long> {
    //작성자 권재현
    void deleteByRecipe(Recipe recipe);
    List<Images> findByRecipe(Recipe recipe);
}
