package com.example.jubtibe.controller;

import com.example.jubtibe.domain.recipe.dto.RecipeRequestDto;
import com.example.jubtibe.domain.recipe.service.RecipeService;
import com.example.jubtibe.dto.StatusResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class RecipeController {
    //    작성자 박성민
    private final RecipeService recipeService;

    @PostMapping("/recipe")
    public StatusResponseDto createRecipe(@RequestBody RecipeRequestDto requestDto){
        return recipeService.createRecipe(requestDto);
    }

    @GetMapping("/recipe")
    public List<RecipeRequestDto> getRecipes(){
        return recipeService.getRecipe();
    }

    @GetMapping("/recipe/{id}")
    public StatusResponseDto getRecipe(@PathVariable Long id){
        return recipeService.getRecipe(id);
    }

    @PutMapping("/recipe/{id}")
    public StatusResponseDto updateRecipe(@PathVariable Long id, @RequestBody RecipeRequestDto requestDto){
        return recipeService.updateRecipe(id, requestDto);
    }

    @DeleteMapping("/recipe/{id}")
    public StatusResponseDto delete(@PathVariable Long id, @RequestBody RecipeRequestDto requestDto){
        return recipeService.delteRecipe(id, requestDto);
    }
}
