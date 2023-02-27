package com.example.jubtibe.domain.recipe.dto;

import com.example.jubtibe.domain.like.entity.Like;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import com.example.jubtibe.domain.user.entity.UserMbti;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
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
    private Like like;

    public RecipeSearchDto(Recipe recipe) {
        this.id = recipe.getRecipeId();
        this.nickname = recipe.getUser().getNickname();
        this.title = recipe.getTitle();
        this.mbti = recipe.getUserMbti();
        this.like = recipe.getLike();
    }
}
