package com.example.jubtibe.controller;

import com.example.jubtibe.domain.user.entity.User;
import com.example.jubtibe.dto.AccessTokenResponseDto;
import com.example.jubtibe.jwt.JwtUtil;
import com.example.jubtibe.security.UserDetailsImpl;
import com.example.jubtibe.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.example.jubtibe.jwt.JwtUtil.REFRESH_TOKEN_HEADER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController {

    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @PostMapping("/access-token/issue")
    public ResponseEntity<AccessTokenResponseDto> createAccessToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request){
        User user = userDetails.getUser();
        String refreshToken = jwtUtil.resolveRefreshToken(request); // 헤더에서 refresh토큰 가져옴\
        AccessTokenResponseDto accessTokenByRefreshToken = tokenService.createAccessTokenByRefreshToken(refreshToken);
        return ResponseEntity.ok(accessTokenByRefreshToken);

    }

}
