package com.example.testTechnique.services;

import com.example.testTechnique.entities.User;
import com.example.testTechnique.exceptions.RecordNotFoundException;
import com.example.testTechnique.repositories.UserRepository;
import com.example.testTechnique.requests.AuthenticationRequest;
import com.example.testTechnique.responses.AuthenticationResponse;
import com.example.testTechnique.utils.EmailValidator;
import com.example.testTechnique.utils.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    final static String USER_NOT_FOUND_CONFIRMATION_MSG = "User with email %s not found while confirming token !";

    private final UserRepository utilisateurRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailValidator emailValidator;


    @Autowired
    private JwtUtil jwtUtil;

    public UserService(UserRepository utilisateurRepository, BCryptPasswordEncoder bCryptPasswordEncoder, EmailValidator emailValidator, JwtUtil jwtUtil) {
        this.utilisateurRepository = utilisateurRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailValidator = emailValidator;
        this.jwtUtil = jwtUtil;
    }

    public User register(User request) throws RecordNotFoundException {
        boolean isValidEmail = emailValidator.test(request.getUsername());
        if (!isValidEmail) {
            throw new IllegalStateException("Email not valid");
        }
        User utilisateur = signUpUser(request);
        return utilisateur;
    }

    public Optional<User> fetchUserById(final Integer userId){
        return utilisateurRepository.findById(userId);
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    public User signUpUser(User utilisateur) {
        boolean userExists = utilisateurRepository.findByUsername(utilisateur.getUsername())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("Email already taken.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(encodedPassword);

        return utilisateurRepository.save(utilisateur);
        //return utilisateur;
    }

    public boolean connexionUtilisateur(User utilisateur) {
        Optional<User> savedUser = utilisateurRepository.findByUsername(utilisateur.getUsername());
        if (savedUser.isPresent()) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (bCryptPasswordEncoder.matches(utilisateur.getPassword(), savedUser.get().getPassword()) &&
                    savedUser.get().isEnabled()
            )
                return true;
        }
        return false;
    }

    public void enableUtilisateur(String email) {
        Optional<User> utilisateur = utilisateurRepository.findByUsername(email);
        if (utilisateur.isPresent()) {
            User user = utilisateur.get();
            user.setEnabled(true);
            utilisateurRepository.save(user);
        } else {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_CONFIRMATION_MSG, email));
        }
    }

    public User updateUser(User utilisateur) {
        String encodedPassword = bCryptPasswordEncoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(encodedPassword);

        return utilisateurRepository.save(utilisateur);
    }

    public User authenticateUtilisateur(String email, String mdp) {
        Optional<User> utilisateur = utilisateurRepository.findByUsername(email);
        if (utilisateur.isPresent()) {
            User user = utilisateur.get();
            boolean encodedPassword = bCryptPasswordEncoder.matches(mdp, user.getPassword());
            return encodedPassword ? user : null;
        } else {
            return null;
        }
    }

    public ResponseEntity<?> userConnexion(User authenticationRequest) throws Exception {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        Map<String, Object> map = new HashMap<>();
        User authenticated = authenticateUtilisateur(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if (authenticated != null) {
            if (authenticated.isEnabled()) {
                final UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());
                final String jwt = jwtUtil.generateToken(userDetails);
                map.put("id", authenticated.getId());
                map.put("username", authenticated.getUsername());
                map.put("role", authenticated.getRole().toString());
                ResponseCookie resCookie = ResponseCookie.from("JWT", jwt)
                        .httpOnly(true)
                        .sameSite("None")
                        .secure(true)
                        .path("/")
                        .maxAge(1 * 1 * 60 * 60)
                        .build();
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.add(HttpHeaders.AUTHORIZATION, jwt);
                responseHeaders.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);

                return ResponseEntity.ok().headers(responseHeaders).body(map);
            } else {
                authenticationResponse = new AuthenticationResponse("", "Votre compte n'est pas activé");
                map.put("authentication", authenticationResponse);
                return ResponseEntity.status(500).body(map);
            }
        } else {
            String error = "Incorrect username or password";
            authenticationResponse = new AuthenticationResponse("", error);
            map.put("authentication", authenticationResponse);
            return ResponseEntity.status(500).body(map);
        }
    }
}
