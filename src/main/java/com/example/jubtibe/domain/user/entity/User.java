package com.example.jubtibe.domain.user.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class User {
    //작성자 : 권재현
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Mbti mbti;
}