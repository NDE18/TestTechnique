package com.example.testTechnique.repositories;

import com.example.testTechnique.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String name);
    User findById(int id);
}
