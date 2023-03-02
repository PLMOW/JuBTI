package com.example.jubtibe.service;

import com.example.jubtibe.domain.user.entity.User;
import com.example.jubtibe.dto.AccessTokenResponseDto;
import com.example.jubtibe.jwt.JwtUtil;
import com.example.jubtibe.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AccessTokenResponseDto createAccessTokenByRefreshToken(String refreshToken){
        User user = userService.findUserByAuthentication();
        Date accessTokenExpireTime = jwtUtil.createAccessTokenExpireTime();
        jwtUtil.validateToken(refreshToken);
        String accessToken = jwtUtil.createAccessToken(user.getUsername(), user.getRole());
        return AccessTokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }
}
