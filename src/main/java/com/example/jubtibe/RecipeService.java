package com.example.jubtibe;

import com.example.jubtibe.RecipeRepository;
import com.example.jubtibe.domain.recipe.dto.RecipeRequestDto;
import com.example.jubtibe.domain.recipe.dto.RecipeResponseDto;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import com.example.jubtibe.dto.StatusResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    // 작성자 박성민
    private final RecipeRepository recipeRepository;

    @Transactional
    public StatusResponseDto createRecipe(RecipeRequestDto requestDto) {
        recipeRepository.save(new Recipe(requestDto));
        return StatusResponseDto.success(200, "조회 성공");
    }

    @Transactional(readOnly = true)
    public List<RecipeResponseDto> getRecipes() {
        List<Recipe> recipeList = recipeRepository.findAllByOrderByCreatedAtDesc();
        List<RecipeResponseDto> responseDtoList = new ArrayList<>();
        for(Recipe recipe : recipeList){
            responseDtoList.add(new RecipeResponseDto(recipe, List<Comments> comments));
        }
        return responseDtoList;
    }

    @Transactional(readOnly = true)
    public RecipeResponseDto getRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("레시피를 찾을 수 없습니다.")
        );
        RecipeResponseDto responseDto = new RecipeResponseDto(recipe,recipe.getComments());
        return responseDto;
    }

    @Transactional
    public StatusResponseDto updateRecipe(Long id, RecipeRequestDto requestDto){
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("레시피를 찾을 수 없습니다.")
        );

        recipe.update(requestDto);
        return StatusResponseDto.success(200, "수정완료");
    }

    @Transactional
    public StatusResponseDto deleteRecipe(Long id){
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("레시피를 찾을 수 없습니다.")
        );

        recipeRepository.delete(recipe);
        return StatusResponseDto.success(200, "삭제완료");
    }
}
