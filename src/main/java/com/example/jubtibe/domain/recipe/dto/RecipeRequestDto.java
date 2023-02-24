package com.example.jubtibe.domain.recipe.dto;

import lombok.Getter;

@Getter
public class RecipeRequestDto {
    //    작성자 박성민
    //    이미지 파일 받아오기
    private String title;
    private Enum userMbti;
    private String material;
    private String content;
}
