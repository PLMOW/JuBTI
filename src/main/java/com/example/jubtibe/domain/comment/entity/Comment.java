package com.example.jubtibe.domain.comment.entity;

import com.example.jubtibe.domain.user.entity.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipeId")
    private Recipe reciqe;
}
