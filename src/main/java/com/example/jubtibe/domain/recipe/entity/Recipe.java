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
import java.time.LocalDateTime;
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
    private UserMbti mbti;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String material;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeLike> recipeLikes = new ArrayList<>();

    public Recipe(RecipeRequestDto requestDto, User user) {
        this.image = requestDto.getImage();
        this.title = requestDto.getTitle();
        this.mbti = requestDto.getMbti();
        this.content = requestDto.getContent();
        this.material = requestDto.getMaterial();
        this.user = user;
    }

    public void update(RecipeRequestDto requestDto) {
        this.image = requestDto.getImage();
        this.title = requestDto.getTitle();
        this.mbti = requestDto.getMbti();
        this.content = requestDto.getContent();
        this.material = requestDto.getMaterial();
    }
}
