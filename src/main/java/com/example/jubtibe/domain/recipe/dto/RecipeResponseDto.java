package com.example.jubtibe.domain.recipe.dto;

import com.example.jubtibe.domain.comment.entity.Comment;
import com.example.jubtibe.domain.like.entity.RecipeLike;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import com.example.jubtibe.domain.user.entity.UserMbti;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class RecipeResponseDto {
    //    작성자 박성민
    //    이미지파일 받아오기
    private Long id;
    private String nickname;
    private String title;
    private String material;
    private String content;
    private UserMbti mbti;
    private RecipeLike recipeLike;
    private List<Comment> comments;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public RecipeResponseDto(Recipe recipe, List<Comment> comments) {
        this.id = recipe.getId();
        this.nickname = recipe.getUser().getNickname();
        this.title = recipe.getTitle();
        this.material = recipe.getMaterial();
        this.content = recipe.getContent();
        this.mbti = recipe.getMbti();
        this.comments = comments;
        this.createdAt = recipe.getCreatedAt();
        this.modifiedAt = recipe.getModifiedAt();
    }
}
