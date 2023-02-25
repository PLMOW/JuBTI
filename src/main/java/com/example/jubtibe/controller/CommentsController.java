package com.example.jubtibe.controller;


import com.example.jubtibe.domain.comments.dto.CommentsRequestDto;
import com.example.jubtibe.dto.StatusResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentsController {
    //작성자 권재현
    private final CommentsService commentsService;

    @PostMapping("/recipe/{id}/comment")
    public StatusResponseDto createComments(@PathVariable Long id, @RequestBody CommentsRequestDto requestDto) {
        return commentsService.createComments(id,requestDto);
    }

    @PutMapping("/recipe/{id}/comment")//수정필요 api에 commentid가 안적혀있음
    public StatusResponseDto updateComments(@PathVariable Long id, @RequestBody CommentsRequestDto requestDto){
        return commentsService.updateComments(id,requestDto);
    }
    @DeleteMapping("/recipe/comment/{commentId}")
    public StatusResponseDto deleteComments(@PathVariable Long id @RequestBody CommentsRequestDto requestdto){
        return commentsService.deleteComments(id,requestdto)
    }
}
