package com.example.jubtibe.domain.like.dto;

import lombok.Getter;

@Getter
public class RecipeLikeRequestDto {
    private Long userId;
    private Long recipeId;
}
