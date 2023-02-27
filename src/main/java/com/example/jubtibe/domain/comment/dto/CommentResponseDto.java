package com.example.jubtibe.domain.comment.dto;

import com.example.jubtibe.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long id;
    private String user;
    private String Comments;
    public CommentResponseDto(Comment comment){
        this.id=comment.getId();
        this.user=comment.getUser().getNickname();
        this.Comments= comment.getComments();
    }
}
