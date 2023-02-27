package com.example.jubtibe.domain.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "users")
@NoArgsConstructor
public class User {
    //작성자 : 권재현 , 조영준
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
    private UserMbti userMbti;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String nickname, String password, UserRoleEnum role, UserMbti userMbti) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.userMbti = userMbti;
        this.role = role;
    }
}