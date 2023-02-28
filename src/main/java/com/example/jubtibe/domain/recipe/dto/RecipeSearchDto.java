package com.example.jubtibe.domain.recipe.dto;

import com.example.jubtibe.domain.like.entity.RecipeLike;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import com.example.jubtibe.domain.user.entity.UserMbti;
import lombok.Getter;
import lombok.NoArgsConstructor;
// 작성자 박성민

@Getter
@NoArgsConstructor
public class RecipeSearchDto {
    //    작성자 박성민
    //    이미지파일 받아오기
    private Long id;
    private String nickname;
    private String title;
    private UserMbti mbti;
    private Integer recipeLike;

    public RecipeSearchDto(Recipe recipe, Integer recipeLike) {
        this.id = recipe.getId();
        this.nickname = recipe.getUser().getNickname();
        this.title = recipe.getTitle();
        this.mbti = recipe.getMbti();
        this.recipeLike = recipeLike;
    }
}
