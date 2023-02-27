package com.example.jubtibe.domain.user.dto;

import com.example.jubtibe.domain.user.entity.UserMbti;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;


@Getter
public class SignUpRequestDto {
    //작성자 권재현 , 조영준
    @Pattern(regexp = "[a-z0-9]{4,10}")
    private String username;
    private String nickname;
    @Pattern(regexp = "[!@#$%^&*][A-Za-z0-9]{8,15}")
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserMbti mbti;

    private boolean admin = false;

    private String adminToken = "";
}
