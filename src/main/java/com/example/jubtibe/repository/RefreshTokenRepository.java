package com.example.jubtibe.repository;

import com.example.jubtibe.domain.user.entity.User;
import com.example.jubtibe.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByUser(User user);

    RefreshToken findByUser(User user);
}
