package com.example.testTechnique.services;

import com.example.testTechnique.entities.Client;
import com.example.testTechnique.repositories.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    public String saveClient(Client client){
        Client existed = clientRepository.findByPhone(client.getPhone());
        if(existed != null){
            return "This phone number is already taken";
        }
        else{
            clientRepository.save(client);
            return "Client has been saved successfully";
        }

    }
}
