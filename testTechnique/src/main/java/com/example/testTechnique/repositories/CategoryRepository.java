package com.example.testTechnique.repositories;

import com.example.testTechnique.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
