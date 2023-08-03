package com.example.testTechnique.controllers;

import com.example.testTechnique.entities.Client;
import com.example.testTechnique.entities.User;
import com.example.testTechnique.exceptions.RecordNotFoundException;
import com.example.testTechnique.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class ClientController {

    public ClientService clientService;
    @PostMapping("/user/registration")
    public String register(@RequestBody Client request){
        return clientService.saveClient(request);
    }
}
