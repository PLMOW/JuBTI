package com.example.jubtibe.service;

import com.example.jubtibe.domain.recipe.dto.RecipeRequestDto;
import com.example.jubtibe.domain.recipe.dto.RecipeResponseDto;
import com.example.jubtibe.domain.recipe.dto.RecipeSearchDto;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import com.example.jubtibe.domain.user.entity.User;
import com.example.jubtibe.domain.user.entity.UserRoleEnum;
import com.example.jubtibe.dto.StatusResponseDto;
import com.example.jubtibe.exception.CustomException;
import com.example.jubtibe.exception.ErrorCode;
import com.example.jubtibe.repository.RecipeLikeRepository;
import com.example.jubtibe.repository.RecipeRepository;
import com.example.jubtibe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
@RequiredArgsConstructor
public class RecipeService {
    // 작성자 박성민
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RecipeLikeRepository recipeLikeRepository;

    @Transactional
    public StatusResponseDto createRecipe(RecipeRequestDto requestDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_CLIENT)
        );
        recipeRepository.save(new Recipe(requestDto, user));

        return StatusResponseDto.builder()
                .statusCode(200)
                .msg("작성 완료")
                .build();
    }
    @Transactional(readOnly = true)
    public List<RecipeSearchDto> getRecipes() {
        List<Recipe> recipeList = recipeRepository.findAllByOrderByCreatedAtDesc();
        List<RecipeSearchDto> responseDtoList = new ArrayList<>();
        for(Recipe recipe : recipeList){
            responseDtoList.add(new RecipeSearchDto(recipe));
        }
        return responseDtoList;
    }

    @Transactional(readOnly = true)
    public RecipeResponseDto getRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RECIPE));
        int likes = recipeLikeRepository.countByRecipe(recipe);
        return new RecipeResponseDto(recipe,likes);
    }

    @Transactional
    public StatusResponseDto updateRecipe(Long id, RecipeRequestDto requestDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_CLIENT)
        );
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_RECIPE)
        );

        if (user.getRole()==(UserRoleEnum.ADMIN) || recipe.getUser().getUsername().equals(username)){
            recipe.update(requestDto);
        }else new CustomException(ErrorCode.UNAUTHORIZED_USER);
        return StatusResponseDto.builder()
                .statusCode(200)
                .msg("수정 완료")
                .build();
    }

    @Transactional
    public StatusResponseDto deleteRecipe(Long id, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_CLIENT)
        );
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_RECIPE)
        );

        if (user.getRole().equals(UserRoleEnum.ADMIN) || recipe.getUser().getUsername().equals(username)){
            recipeRepository.delete(recipe);
        }else new CustomException(ErrorCode.UNAUTHORIZED_USER);

        return StatusResponseDto.builder()
                .statusCode(200)
                .msg("삭제 완료")
                .build();
    }
}
