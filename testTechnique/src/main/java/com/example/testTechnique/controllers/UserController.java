package com.example.testTechnique.controllers;

import com.example.testTechnique.entities.User;
import com.example.testTechnique.exceptions.RecordNotFoundException;
import com.example.testTechnique.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class UserController {

    public UserService userService;

    @PostMapping("/user/registration")
    public User register(@RequestBody User request) throws RecordNotFoundException {
        return userService.register(request);
    }

    @PostMapping("/user/connexion")
    public ResponseEntity<?> connexion(@RequestBody User request) throws Exception {
        return userService.userConnexion(request);
    }

}
