package com.example.jubtibe.domain.recipe.entity;

import com.example.jubtibe.domain.comment.entity.Comment;
import com.example.jubtibe.domain.like.entity.RecipeLike;
import com.example.jubtibe.domain.recipe.dto.RecipeRequestDto;
import com.example.jubtibe.domain.user.entity.User;
import com.example.jubtibe.domain.user.entity.UserMbti;
import com.example.jubtibe.entity.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Recipe extends Timestamped {
    //    작성자 박성민

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length=1000,nullable = false)
    private String image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserMbti mbti;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeLike> recipeLike = new ArrayList<>();

    public Recipe(RecipeRequestDto requestDto, User user) {
        this.image = requestDto.getImage();
        this.title = requestDto.getTitle();
        this.mbti = requestDto.getMbti();
        this.material = requestDto.getMaterial();
        this.content = requestDto.getContent();
        this.user = user;
//        this.recipeLike = requestDto.getRecipeLike;
    }

    public void update(RecipeRequestDto requestDto) {
        this.image = requestDto.getImage();
        this.title = requestDto.getTitle();
        this.mbti = requestDto.getMbti();
        this.material = requestDto.getMaterial();
        this.content = requestDto.getContent();
    }
}
