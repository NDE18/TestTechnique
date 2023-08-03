package com.example.testTechnique.repositories;

import com.example.testTechnique.entities.Client;
import com.example.testTechnique.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByPhone(String name);
}
