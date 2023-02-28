package com.example.jubtibe.domain.user.dto;

import com.example.jubtibe.domain.user.entity.UserMbti;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;


@Getter
public class SignUpRequestDto {
    //작성자 권재현 , 조영준
    @Schema(description = "영어 소문자, 0~9까지 숫자를 조합한 4~10자리의 아이디만 허용됨", example = "0hyeon",required = true)
    @Pattern(regexp = "[a-z0-9]{4,10}")
    private String username;
    @Schema(description = "이용할 회원의 닉네임", required = true)
    private String nickname;

    @Schema(description = "특수문자, 대,소문자가 들어간 알파벳을 조합한 8~15자의 비밀번호만 허용됨", required = true)
    @Pattern(regexp = "[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"A-Za-z0-9]{8,15}",message = "대소문자와 숫자만 가능합니다")
    private String password;
    @Schema(description = "가입할 유저의 MBTI 확인", example = "INFP", required = true)
    @Enumerated(value = EnumType.STRING)
    private UserMbti mbti;

    private boolean admin = false;

    private String adminToken = "";
}
