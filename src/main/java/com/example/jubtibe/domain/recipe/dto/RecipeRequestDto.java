package com.example.jubtibe.domain.recipe.dto;

import com.example.jubtibe.domain.user.entity.UserMbti;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
public class RecipeRequestDto {
    //    작성자 박성민
    //    이미지 파일 받아오기
    private String image;
    private String title;
    @Enumerated(value = EnumType.STRING)
    private UserMbti mbti;
    private String material;
    private String content;
}
