package com.example.jubtibe.domain.like.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class RecipeLikeRequestDto {
    private Long userId;
    private Long RecipeId;
}
