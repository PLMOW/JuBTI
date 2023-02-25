package com.example.jubtibe.repository;

import com.example.jubtibe.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //작성자 권재현, 조영준
    Optional<User> findByUsername(String username);
}
