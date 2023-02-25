package com.example.jubtibe.domain.recipe.entity;

import com.example.jubtibe.domain.comment.entity.Comment;
import com.example.jubtibe.domain.like.entity.Like;
import com.example.jubtibe.domain.recipe.dto.RecipeRequestDto;
import com.example.jubtibe.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Recipe {
    //    작성자 박성민

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Enum userMbti;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String material;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany
    @JoinColumn(name = "commentId")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "likeId")
    private Like like;

    public Recipe(RecipeRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.userMbti = requestDto.getUserMbti();
        this.content = requestDto.getContent();
        this.material = requestDto.getMaterial();
    }

    public void update(RecipeRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.userMbti = requestDto.getUserMbti();
        this.content = requestDto.getContent();
        this.material = requestDto.getMaterial();
    }
}
