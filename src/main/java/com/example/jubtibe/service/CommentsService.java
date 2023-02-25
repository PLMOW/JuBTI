package com.example.jubtibe.service;

import com.example.jubtibe.RecipeRepository;
import com.example.jubtibe.domain.comments.dto.CommentsRequestDto;
import com.example.jubtibe.domain.comments.entity.Comments;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import com.example.jubtibe.dto.StatusResponseDto;
import com.example.jubtibe.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final RecipeRepository recipeRepository;
    private final CommentsRepository commentsRepository;

    @Transactional
    public StatusResponseDto createComments(Long id, CommentsRequestDto requestDto) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        commentsRepository.save(new Comments(recipe,requestDto));
        return StatusResponseDto.success(200,"작성 완료");
    }
    @Transactional
    public StatusResponseDto updateComments(Long id, CommentsRequestDto requestDto) {

    }



}
