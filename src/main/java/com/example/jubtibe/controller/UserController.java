package com.example.jubtibe.controller;

import com.example.jubtibe.domain.user.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    //작성자 조영준,권재현
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return ResponseEntity.status(400).body(bindingResult.getAllErrors());
        userService.signUp();
        return ResponseEntity.ok()
    }
}
