package com.example.jubtibe.controller;

import com.example.jubtibe.domain.recipe.dto.RecipeRequestDto;
import com.example.jubtibe.domain.recipe.dto.RecipeResponseDto;
import com.example.jubtibe.domain.recipe.dto.RecipeSearchDto;
import com.example.jubtibe.security.UserDetailsImpl;
import com.example.jubtibe.service.RecipeService;
import com.example.jubtibe.dto.StatusResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class RecipeController {
    //    작성자 박성민
    private final RecipeService recipeService;

    @PostMapping("/recipe")
    public StatusResponseDto createRecipe(@RequestBody RecipeRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return recipeService.createRecipe(requestDto, userDetails.getUsername());
    }

    @GetMapping("/recipe")
    public List<RecipeSearchDto> getRecipes(){
        return recipeService.getRecipes();
    }

    @GetMapping("/recipe/{id}")
    public RecipeResponseDto getRecipe(@PathVariable Long id){
        return recipeService.getRecipe(id);
    }

    @PutMapping("/recipe/{id}")
    public StatusResponseDto updateRecipe(@PathVariable Long id, @RequestBody RecipeRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return recipeService.updateRecipe(id, requestDto, userDetails.getUsername());
    }

    @DeleteMapping("/recipe/{id}")
    public StatusResponseDto deleteRecipe(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return recipeService.deleteRecipe(id, userDetails.getUsername());
    }
    
}
