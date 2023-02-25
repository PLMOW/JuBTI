package com.example.jubtibe.controller;


import com.example.jubtibe.domain.comment.dto.CommentRequestDto;
import com.example.jubtibe.dto.StatusResponseDto;
import com.example.jubtibe.security.UserDetailsImpl;
import com.example.jubtibe.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentController {
    //작성자 권재현
    private final CommentService commentService;

    @PostMapping("/recipe/{id}/comment")
    public StatusResponseDto createComments(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComments(id,requestDto,userDetails.getUsername());
    }

    @PutMapping("/recipe/comment/{commentId}")
    public StatusResponseDto updateComments(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComments(commentId,requestDto,userDetails.getUsername());
    }
    @DeleteMapping("/recipe/comment/{commentId}")
    public StatusResponseDto deleteComments(@PathVariable Long commentId ,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComments(commentId,userDetails.getUsername());
    }
//    @PostMapping("/post") // 게시글 작성
//    public ResponseEntity<?> postLetter(@RequestBody LetterRequestDto letterRequestDto, ){
//        return letterService.postLetter(letterRequestDto,userDetails.getUser());
//    }
}
