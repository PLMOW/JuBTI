package com.example.jubtibe.controller;

import com.example.jubtibe.dto.StatusResponseDto;
import com.example.jubtibe.security.UserDetailsImpl;
import com.example.jubtibe.service.RecipeLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipe/{id}/like")
public class RecipeLikeController {
    // 작성자 박성민
    private final RecipeLikeService recipeLikeService;

    @PostMapping
    public StatusResponseDto like(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return recipeLikeService.like(id, userDetails.getUsername());
    }

    @DeleteMapping
    public StatusResponseDto dislike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return recipeLikeService.dislike(id, userDetails.getUsername());
    }
}
