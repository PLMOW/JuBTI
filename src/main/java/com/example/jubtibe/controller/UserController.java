package com.example.jubtibe.controller;

import com.example.jubtibe.domain.user.dto.LoginRequestDto;
import com.example.jubtibe.domain.user.dto.SignUpRequestDto;
import com.example.jubtibe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    //작성자 조영준,권재현
    private final UserService userService;

    @PostMapping("/signup") // 회원가입
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequestDto signupRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return ResponseEntity.status(400).body(bindingResult.getAllErrors());
        userService.signUp(signupRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PostMapping("/login")//로그인
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response){
        userService.login(requestDto,response);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
