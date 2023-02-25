package com.example.jubtibe.service;

import com.example.jubtibe.domain.comment.dto.CommentsRequestDto;
import com.example.jubtibe.domain.comment.entity.Comments;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import com.example.jubtibe.domain.user.entity.User;
import com.example.jubtibe.dto.StatusResponseDto;
import com.example.jubtibe.repository.CommentsRepository;
import com.example.jubtibe.repository.RecipeRepository;
import com.example.jubtibe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentsService {
    //작성자 권재현
    private final RecipeRepository recipeRepository;
    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;

    @Transactional
    public StatusResponseDto createComments(Long id, CommentsRequestDto requestDto,String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException());
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        commentsRepository.save(new Comments(user,recipe,requestDto));
        return StatusResponseDto.success(200,"작성 완료");
    }
    @Transactional
    public StatusResponseDto updateComments(Long commentsid, CommentsRequestDto requestDto,String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException());
        Comments comments = commentsRepository.findById(commentsid).orElseThrow(()->new IllegalArgumentException());
        if(!comments.getUser().equals(user)){
            new IllegalArgumentException("본인 댓글만 수정 가능");
        }
        comments.update(requestDto);
        return StatusResponseDto.success(200,"수정 완료");
    }

    @Transactional
    public StatusResponseDto deleteComments(Long commentsId,String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException());
        Comments comments = commentsRepository.findById(commentsId).orElseThrow(()->new IllegalArgumentException());
        if(!comments.getUser().equals(user)){
            new IllegalArgumentException("본인 댓글만 삭제 가능");
        }
        commentsRepository.deleteById(commentsId);
        return StatusResponseDto.success(200,"삭제 완료");
    }
}
