package com.example.jubtibe.repository;

import com.example.jubtibe.domain.comment.entity.Comment;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //작성자 권재현
    List<Comment> findByRecipe(Recipe recipe);
}
