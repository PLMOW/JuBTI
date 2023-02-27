package com.example.jubtibe.repository;

import com.example.jubtibe.domain.like.entity.RecipeLike;
import com.example.jubtibe.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Long> {
    int countByRecipe();
    Optional<RecipeLike> findByUser(User user);
    void deleteByUser(User user);
}
