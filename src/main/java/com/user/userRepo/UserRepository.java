package com.user.userRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

