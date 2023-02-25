package com.example.jubtibe.repository;

import com.example.jubtibe.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //작성자 권재현
}
