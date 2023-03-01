package com.example.jubtibe.service;

import com.example.jubtibe.domain.comment.dto.CommentResponseDto;
import com.example.jubtibe.domain.comment.entity.Comment;
import com.example.jubtibe.domain.like.entity.RecipeLike;
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
import com.example.jubtibe.repository.CommentRepository;
import com.example.jubtibe.repository.RecipeRepository;
import com.example.jubtibe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    // 작성자 박성민
    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final RecipeLikeRepository recipeLikeRepository;
    @Autowired
    private UploadService uploadService;

    @Transactional
    public StatusResponseDto createRecipe(RecipeRequestDto requestDto, String username, MultipartFile image)throws IOException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_CLIENT)
        );
        String storedFileName = uploadService.upload(image, "images");
        recipeRepository.save(new Recipe(requestDto, user,storedFileName));

        return StatusResponseDto.builder()
                .statusCode(200)
                .msg("작성 완료")
                .build();
    }

    @Transactional(readOnly = true)
    public List<RecipeSearchDto> getRecipes() {
        List<Recipe> recipeList = recipeRepository.findAllByOrderByRecipeLikeDesc();
        List<RecipeSearchDto> responseDtoList = new ArrayList<>();
        for(Recipe recipe : recipeList){
            responseDtoList.add(new RecipeSearchDto(recipe, recipeLikeRepository.countByRecipe(recipe)));
        }
        return responseDtoList;
    }

    @Transactional(readOnly = true)
    public List<RecipeSearchDto> getRecipes(int a, int b) {
        if (a >= b) throw new CustomException(ErrorCode.INVALID_REQUEST);
        List<Recipe> recipeList = recipeRepository.findAllByOrderByRecipeLikeDesc();
        List<RecipeSearchDto> responseDtoList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            responseDtoList.add(new RecipeSearchDto(recipe, recipeLikeRepository.countByRecipe(recipe)));
        }
        List<RecipeSearchDto> answer = new ArrayList<>();
        for(int i=0; i<=b-a; i++){
            if(responseDtoList.size()>b){
                answer = new ArrayList<>(responseDtoList.subList(a-1, b));
            }else if(responseDtoList.size()>a-1+i){
                answer.add(responseDtoList.get(a - 1 + i));
            }
        }
        return answer;
    }

    @Transactional(readOnly = true)
    public RecipeResponseDto getRecipe(Long id, String username) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RECIPE));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CLIENT));

        int hasLike = 0;
        if(recipeLikeRepository.findByUser(user).isPresent()) hasLike = 1;

        List<Comment> comment = commentRepository.findByRecipe(recipe);
        int likes = recipeLikeRepository.countByRecipe(recipe);
        List<CommentResponseDto> commentResponse =new ArrayList<>();
        for (Comment res : comment) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(res);
            commentResponse.add(commentResponseDto);
        }
        return new RecipeResponseDto(recipe,commentResponse,likes, hasLike);
    }

    @Transactional
    public StatusResponseDto updateRecipe(Long id, RecipeRequestDto requestDto, String username,MultipartFile image)throws IOException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_CLIENT)
        );
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_RECIPE)
        );
        if (user.getRole()==(UserRoleEnum.ADMIN) || recipe.getUser().getUsername().equals(username)){
            String storedFileName = uploadService.upload(image, "images");
            recipeRepository.save(new Recipe(requestDto, user,storedFileName));
            recipe.update(requestDto,storedFileName);
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
