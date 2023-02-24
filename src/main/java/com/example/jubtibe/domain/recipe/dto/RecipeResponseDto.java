package com.example.jubtibe.domain.recipe.dto;

import com.example.jubtibe.domain.comments.entity.Comments;
import com.example.jubtibe.domain.like.entity.Like;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class RecipeResponseDto {
    //    작성자 박성민
    //    이미지파일 받아오기
    private String nickname;
    private String title;
    private String material;
    private String content;
    private Enum userMbti;
    private Like like;
    private List<Comments> comments;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public RecipeResponseDto(Recipe recipe, List<Comments> comments) {
        this.nickname = recipe.getUser().getNickname();
        this.title = recipe.getTitle();
        this.material = recipe.getMaterial();
        this.content = recipe.getContent();
        this.userMbti = recipe.getUserMbti();
        this.like = recipe.getLike();
        this.comments = comments;
    }
}
