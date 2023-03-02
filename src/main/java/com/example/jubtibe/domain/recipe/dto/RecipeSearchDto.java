package com.example.jubtibe.domain.recipe.dto;

import com.example.jubtibe.domain.recipe.entity.Recipe;
import com.example.jubtibe.domain.user.entity.UserMbti;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
// 작성자 박성민

@Getter
@NoArgsConstructor
public class RecipeSearchDto {
    //    작성자 박성민
    //    이미지파일 받아오기
    private Long id;
    private List image;
    private String nickname;
    private String title;
    private UserMbti mbti;
    private Integer recipeLike;

    public RecipeSearchDto(Recipe recipe, Integer recipeLike,List image) {
        this.image= image;
        this.id = recipe.getId();
        this.nickname = recipe.getUser().getNickname();
        this.title = recipe.getTitle();
        this.mbti = recipe.getMbti();
        this.recipeLike = recipeLike;
    }
}
