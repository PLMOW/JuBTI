package com.example.jubtibe.controller;


import com.example.jubtibe.domain.comments.dto.CommentsRequestDto;
import com.example.jubtibe.dto.StatusResponseDto;
import com.example.jubtibe.security.UserDetailsImpl;
import com.example.jubtibe.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentsController {
    //작성자 권재현
    private final CommentsService commentsService;

    @PostMapping("/recipe/{id}/comment")
    public StatusResponseDto createComments(@PathVariable Long id, @RequestBody CommentsRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentsService.createComments(id,requestDto,userDetails.getUsername());
    }

    @PutMapping("/recipe/comment/{commentId}")
    public StatusResponseDto updateComments(@PathVariable Long commentId, @RequestBody CommentsRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentsService.updateComments(commentId,requestDto,userDetails.getUsername());
    }
    @DeleteMapping("/recipe/comment/{commentId}")
    public StatusResponseDto deleteComments(@PathVariable Long commentId ,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentsService.deleteComments(commentId,userDetails.getUsername());
    }
//    @PostMapping("/post") // 게시글 작성
//    public ResponseEntity<?> postLetter(@RequestBody LetterRequestDto letterRequestDto, ){
//        return letterService.postLetter(letterRequestDto,userDetails.getUser());
//    }
}
