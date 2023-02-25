package com.example.jubtibe.controller;

import com.example.jubtibe.domain.recipe.dto.RecipeRequestDto;
import com.example.jubtibe.domain.recipe.dto.RecipeResponseDto;
import com.example.jubtibe.domain.recipe.dto.RecipeSearchDto;
import com.example.jubtibe.service.RecipeService;
import com.example.jubtibe.dto.StatusResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class RecipeController {
    //    작성자 박성민
    private final RecipeService recipeService;

    @PostMapping("/recipe")
    public StatusResponseDto createRecipe(@Valid @RequestBody RecipeRequestDto requestDto, HttpServletRequest request){
        return recipeService.createRecipe(requestDto, request);
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
    public StatusResponseDto updateRecipe(@Valid @PathVariable Long id, @RequestBody RecipeRequestDto requestDto, HttpServletRequest request){
        return recipeService.updateRecipe(id, requestDto, request);
    }

    @DeleteMapping("/recipe/{id}")
    public StatusResponseDto deleteRecipe(@PathVariable Long id, HttpServletRequest request){
        return recipeService.deleteRecipe(id, request);
    }
    
}
