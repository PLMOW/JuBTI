package com.example.jubtibe.domain.recipe.entity;

import com.example.jubtibe.domain.comment.entity.Comment;
import com.example.jubtibe.domain.like.entity.RecipeLike;
import com.example.jubtibe.domain.recipe.dto.RecipeRequestDto;
import com.example.jubtibe.domain.user.entity.User;
import com.example.jubtibe.domain.user.entity.UserMbti;
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
    private UserMbti mbti;

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

    @OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeLike> recipeLikes = new ArrayList<>();

    public Recipe(RecipeRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.mbti = requestDto.getMbti();
        this.content = requestDto.getContent();
        this.material = requestDto.getMaterial();
    }

    public void update(RecipeRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.mbti = requestDto.getMbti();
        this.content = requestDto.getContent();
        this.material = requestDto.getMaterial();
    }
}
