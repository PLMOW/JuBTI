package com.example.jubtibe.service;

import com.example.jubtibe.domain.comment.dto.CommentRequestDto;
import com.example.jubtibe.domain.comment.entity.Comment;
import com.example.jubtibe.domain.recipe.entity.Recipe;
import com.example.jubtibe.domain.user.entity.User;
import com.example.jubtibe.dto.StatusResponseDto;
import com.example.jubtibe.repository.CommentRepository;
import com.example.jubtibe.repository.RecipeRepository;
import com.example.jubtibe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    //작성자 권재현
    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public StatusResponseDto createComments(Long id, CommentRequestDto requestDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException());
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        commentRepository.save(new Comment(user,recipe,requestDto));
//        return StatusResponseDto.success(200,"작성 완료");
        return StatusResponseDto.builder()
                .statusCode(200)
                .msg("작성 완료")
                .build();
    }
    @Transactional
    public StatusResponseDto<?> updateComments(Long commentsid, CommentRequestDto requestDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException());
        Comment comments = commentRepository.findById(commentsid).orElseThrow(()->new IllegalArgumentException());
        if(!comments.getUser().equals(user)){
            new IllegalArgumentException("본인 댓글만 수정 가능");
        }
        comments.update(requestDto);
        return StatusResponseDto.builder()
                .statusCode(200)
                .msg("수정 완료")
                .build();
    }

    @Transactional
    public StatusResponseDto deleteComments(Long commentsId,String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException());
        Comment comments = commentRepository.findById(commentsId).orElseThrow(()->new IllegalArgumentException());
        if(!comments.getUser().equals(user)){
            new IllegalArgumentException("본인 댓글만 삭제 가능");
        }
        commentRepository.deleteById(commentsId);
        return StatusResponseDto.builder()
                .statusCode(200)
                .msg("삭제 완료")
                .build();
    }
}
