package com.example.jubtibe.service;

import com.example.jubtibe.domain.like.entity.RecipeLike;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import com.example.jubtibe.domain.user.entity.User;
import com.example.jubtibe.dto.StatusResponseDto;
import com.example.jubtibe.exception.CustomException;
import com.example.jubtibe.exception.ErrorCode;
import com.example.jubtibe.repository.RecipeLikeRepository;
import com.example.jubtibe.repository.RecipeRepository;
import com.example.jubtibe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeLikeService {
    // 작성자 박성민
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RecipeLikeRepository recipeLikeRepository;

    @Transactional
    public StatusResponseDto like (Long id, String username){
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_CLIENT)
        );
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("레시피를 찾을 수 없습니다.")
        );
        if (!recipeLikeRepository.findByUser(user).isEmpty()){
            recipeLikeRepository.deleteByUser(user);
            return StatusResponseDto.builder()
                    .statusCode(200)
                    .msg("좋아요 취소")
                    .build();
        }
        else {
            RecipeLike like = new RecipeLike(user, recipe);
            recipeLikeRepository.save(like);
            return StatusResponseDto.builder()
                    .statusCode(200)
                    .msg("좋아요")
                    .build();
        }

    }

//    @Transactional
//    public StatusResponseDto dislike (Long id, String username){
//        User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new CustomException(ErrorCode.NOT_FOUND_CLIENT)
//        );
//        Recipe recipe = recipeRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("레시피를 찾을 수 없습니다.")
//        );
//        recipeRepository.updatecount(recipe, false);
//        return StatusResponseDto.builder()
//                .statusCode(200)
//                .msg("좋아요 취소")
//                .build();
//    }
}
