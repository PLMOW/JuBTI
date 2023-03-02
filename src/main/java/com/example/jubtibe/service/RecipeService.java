package com.example.jubtibe.service;

import com.example.jubtibe.domain.comment.dto.CommentResponseDto;
import com.example.jubtibe.domain.comment.entity.Comment;
import com.example.jubtibe.domain.like.image.dto.ImageResponse;
import com.example.jubtibe.domain.recipe.dto.RecipeRequestDto;
import com.example.jubtibe.domain.recipe.dto.RecipeResponseDto;
import com.example.jubtibe.domain.recipe.dto.RecipeSearchDto;
import com.example.jubtibe.domain.like.image.entity.Images;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import com.example.jubtibe.domain.user.entity.User;
import com.example.jubtibe.domain.user.entity.UserRoleEnum;
import com.example.jubtibe.dto.StatusResponseDto;
import com.example.jubtibe.exception.CustomException;
import com.example.jubtibe.exception.ErrorCode;
import com.example.jubtibe.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
    private final ImageRepository imageRepository;
    @Autowired
    private UploadService uploadService;

    private long fileSizeLimit = 10*1024*1024;//10메가바이트/킬로바이트/바이트

    @Transactional
    public StatusResponseDto createRecipe(RecipeRequestDto requestDto, String username, List<MultipartFile> images)throws IOException {
        if(images.size()>5){throw new IllegalArgumentException("사진을 5장 이하로 넣어주세요");}
        fileSizeCheck(images);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_CLIENT)
        );
        Recipe recipe =new Recipe(requestDto, user);
        recipeRepository.save(recipe);
        for (MultipartFile image : images) {
            if(fileCheck(image))
            {throw new IllegalArgumentException("확장자를 확인해주세요");}
            String storedFileName = uploadService.upload(image, "images");
            imageRepository.save(new Images(storedFileName,recipe));
        }
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
            List<Images> imagesList = imageRepository.findByRecipe(recipe);
            List<ImageResponse> image = new ArrayList<>();
            for (Images images : imagesList) {
                image.add(new ImageResponse(images));
            }
            responseDtoList.add(new RecipeSearchDto(recipe, recipeLikeRepository.countByRecipe(recipe),image));
        }
        return responseDtoList;
    }

    @Transactional(readOnly = true)
    public List<RecipeSearchDto> getRecipes(int a, int b) {
        if (a >= b) throw new CustomException(ErrorCode.INVALID_REQUEST);
        List<Recipe> recipeList = recipeRepository.findAllByOrderByRecipeLikeDesc();
        List<RecipeSearchDto> responseDtoList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            List<Images> imagesList = imageRepository.findByRecipe(recipe);
            List<ImageResponse> image = new ArrayList<>();
            for (Images images : imagesList) {
                image.add(new ImageResponse(images));
            }
            responseDtoList.add(new RecipeSearchDto(recipe, recipeLikeRepository.countByRecipe(recipe),image));
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
        List<Images> imagesList = imageRepository.findByRecipe(recipe);
        List<ImageResponse> image = new ArrayList<>();
        for (Images images : imagesList) {
            image.add(new ImageResponse(images));
        }
        List<Comment> comment = commentRepository.findByRecipe(recipe);
        int likes = recipeLikeRepository.countByRecipe(recipe);
        List<CommentResponseDto> commentResponse =new ArrayList<>();
        for (Comment res : comment) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(res);
            commentResponse.add(commentResponseDto);
        }
        return new RecipeResponseDto(recipe,commentResponse,likes, hasLike,image);
    }

    @Transactional
    public StatusResponseDto updateRecipe(Long id, RecipeRequestDto requestDto, String username,List<MultipartFile> images)throws IOException {
        if(images.size()>5){throw new IllegalArgumentException("사진을 5장 이하로 넣어주세요");}
        fileSizeCheck(images);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CLIENT));
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RECIPE));
        if (user.getRole()==(UserRoleEnum.ADMIN) || recipe.getUser().getUsername().equals(username)){
            imageRepository.deleteByRecipe(recipe);
            recipe.update(requestDto);
            for (MultipartFile image : images) {
                if(fileCheck(image))
                {throw new IllegalArgumentException("확장자를 확인해주세요");}
                String storedFileName = uploadService.upload(image, "images");
                imageRepository.save(new Images(storedFileName,recipe));
            }
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
    private boolean fileCheck(MultipartFile file){
        String fileName = StringUtils.getFilenameExtension(file.getOriginalFilename());//getFilename은 전체이름을 통째로 가져옴
        String exe = fileName.toLowerCase();
        if(exe.equals("jpg")||exe.equals("png")||exe.equals("jpeg")||exe.equals("webp"))
        {return false;}
        return true;
    }

    private void fileSizeCheck(List<MultipartFile> images){
        long fileSize = 0l;
        for (MultipartFile image : images) {
            fileSize+=image.getSize();
        }
        if(fileSize>fileSizeLimit){
            throw new IllegalArgumentException("총 용량 10MB이하만 업로드 가능합니다");}
    }
}
