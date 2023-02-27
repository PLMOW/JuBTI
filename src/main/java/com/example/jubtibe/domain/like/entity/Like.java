package com.example.jubtibe.domain.like.entity;

import com.example.jubtibe.domain.recipe.entity.Recipe;
import com.example.jubtibe.domain.user.entity.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Like {
    //작성자 박성민, 권재현
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipeId")
    private Recipe recipe;
}
