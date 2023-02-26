package com.example.jubtibe.service;

import com.example.jubtibe.domain.recipe.dto.RecipeRequestDto;
import com.example.jubtibe.domain.recipe.dto.RecipeResponseDto;
import com.example.jubtibe.domain.recipe.dto.RecipeSearchDto;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import com.example.jubtibe.domain.user.entity.User;
import com.example.jubtibe.domain.user.entity.UserRoleEnum;
import com.example.jubtibe.dto.StatusResponseDto;
import com.example.jubtibe.repository.RecipeRepository;
import com.example.jubtibe.repository.UserRepository;
import com.example.jubtibe.security.UserDetailsImpl;
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
    private final UserRepository userRepository;

    @Transactional
    public StatusResponseDto createRecipe(RecipeRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("회원가입 후 작성 가능합니다.")
        );
        recipeRepository.save(new Recipe(requestDto));

        return StatusResponseDto.success(200, "레시피 작성 완료");
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
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("레시피를 찾을 수 없습니다.")
        );
        return new RecipeResponseDto(recipe,recipe.getComments());
    }

    @Transactional
    public StatusResponseDto updateRecipe(Long id, RecipeRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("회원가입 후 작성 가능합니다.")
        );
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("레시피를 찾을 수 없습니다.")
        );

        if (user.getRole().equals(UserRoleEnum.ADMIN) || recipe.getUser().getUsername().equals(user.getUsername())){
            recipe.update(requestDto);
        }else return StatusResponseDto.fail(400, "수정 권한이 없습니다.");

        return StatusResponseDto.success(200, "수정완료");
    }

    @Transactional
    public StatusResponseDto deleteRecipe(Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("삭제 권한이 없습니다.")
        );
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("레시피를 찾을 수 없습니다.")
        );

        if (user.getRole().equals(UserRoleEnum.ADMIN) || recipe.getUser().getUsername().equals(user.getUsername())){
            recipeRepository.delete(recipe);
        }else return StatusResponseDto.fail(400, "삭제 권한이 없습니다.");

        return StatusResponseDto.success(200, "삭제완료");
    }
}
