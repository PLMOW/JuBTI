package com.example.jubtibe.repository;

import com.example.jubtibe.domain.comment.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    //작성자 권재현
}
