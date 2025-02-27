package com.example.jubtibe.service;

import com.example.jubtibe.domain.user.dto.LoginRequestDto;
import com.example.jubtibe.domain.user.dto.SignUpRequestDto;
import com.example.jubtibe.domain.user.entity.User;
import com.example.jubtibe.domain.user.entity.UserMbti;
import com.example.jubtibe.domain.user.entity.UserRoleEnum;
import com.example.jubtibe.dto.AccessTokenResponseDto;
import com.example.jubtibe.dto.StatusResponseDto;
import com.example.jubtibe.entity.RefreshToken;
import com.example.jubtibe.exception.CustomException;
import com.example.jubtibe.exception.ErrorCode;
import com.example.jubtibe.jwt.JwtUtil;
import com.example.jubtibe.repository.RefreshTokenRepository;
import com.example.jubtibe.repository.UserRepository;
import com.example.jubtibe.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${admin.token}")
    private String ADMIN_TOKEN;

    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto){
        String username = signUpRequestDto.getUsername();
        String nickname = signUpRequestDto.getNickname();
        String password = passwordEncoder.encode(signUpRequestDto.getPassword());
        UserMbti mbti = signUpRequestDto.getMbti();
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USERS);
        }

        UserRoleEnum role = UserRoleEnum.USER; // 여기서 role 확인까지
        if (signUpRequestDto.isAdmin()) {
            if (!signUpRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, nickname, password, role, mbti);
        userRepository.save(user);
    }

    @Transactional
    public StatusResponseDto<?> login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        Optional<RefreshToken> found = refreshTokenRepository.findById(user.getId());

        if (found.isPresent()) refreshTokenRepository.deleteByUser(user);

        AccessTokenResponseDto token = jwtUtil.createToken(user.getUsername(), user.getRole());

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, token.getRefreshToken());

        StatusResponseDto<UserMbti>login = new StatusResponseDto<>("success",user.getUserMbti(),200);

        return new StatusResponseDto<>("success",user.getUserMbti(),200);
        //        return login.builder()
//                .msg("success")
//                .data(user.getUserMbti())
//                .statusCode(200)
//                .build();
        }

        @Transactional(readOnly = true)
        public User findUserByAuthentication(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
                return null;
            }
            return ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        }

}
