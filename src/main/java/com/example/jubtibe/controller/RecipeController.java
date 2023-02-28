package com.example.jubtibe.controller;

import com.example.jubtibe.domain.recipe.dto.RecipeRequestDto;
import com.example.jubtibe.domain.recipe.dto.RecipeResponseDto;
import com.example.jubtibe.domain.recipe.dto.RecipeSearchDto;
import com.example.jubtibe.security.UserDetailsImpl;
import com.example.jubtibe.service.RecipeService;
import com.example.jubtibe.dto.StatusResponseDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

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

    @Parameters({
            @Parameter(name = "a", description = "처음 불러올 숫자", in = ParameterIn.PATH, required = true),
            @Parameter(name = "b", description = "마지막으로 불러올 숫자", in = ParameterIn.PATH, required = true)
    })
    @GetMapping("/recipe/{a}/{b}")
    public List<RecipeSearchDto> getRecipes(@PathVariable int a, @PathVariable int b){
        return recipeService.getRecipes(a, b);
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
