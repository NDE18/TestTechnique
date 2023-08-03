package com.example.testTechnique.responses;

import com.example.testTechnique.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String jwt;
    private String status;

    public AuthenticationResponse(String status) {
        this.status = status;
    }

    public AuthenticationResponse(String jwt, User utilisateur) {
        this.jwt = jwt;
        this.status = status;
    }

    public List<String> UtilisateurConnecte(String jwt, String status){
        var list = new ArrayList<>(List.of(jwt,status));
        return list;
    }

    public String getJwt() {
        return jwt;
    }

    public String getStatus() {
        return status;
    }
}
