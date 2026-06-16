package com.usermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermicroservice.DTO.LoginRequestDTO;
import com.usermicroservice.DTO.LoginResponseDTO;
import com.usermicroservice.DTO.RegisterRequestDTO;
import com.usermicroservice.DTO.UserResponseDTO;
import com.usermicroservice.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO dto) {
        
        return ResponseEntity.status(201).body(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
    
    @PutMapping("/updatepassword/{email}/{newPassword}")
    public ResponseEntity<String> updatePassword(
            @PathVariable("email") String email,
            @PathVariable("newPassword") String newPassword) {

        return ResponseEntity.ok(authService.updatePassword(email, newPassword));
    }

}