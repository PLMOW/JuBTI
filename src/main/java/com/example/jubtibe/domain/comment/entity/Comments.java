package com.example.jubtibe.domain.comment.entity;

import com.example.jubtibe.domain.comment.dto.CommentsRequestDto;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import com.example.jubtibe.domain.user.entity.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Comments {
    //작성자 권재현
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipeId")
    private Recipe recipe;

    public Comments(User user, Recipe recipe, CommentsRequestDto requestDto){
    this.user=user;
    this.recipe=recipe;
    comments=requestDto.getComments();
    }

    public void update(CommentsRequestDto requestDto) {
        this.comments = requestDto.getComments();
    }
}
