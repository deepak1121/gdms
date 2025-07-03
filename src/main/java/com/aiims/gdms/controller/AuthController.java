package com.aiims.gdms.controller;

import com.aiims.gdms.dto.AuthRequest;
import com.aiims.gdms.dto.AuthResponse;
import com.aiims.gdms.dto.LoginRequest;
import com.aiims.gdms.service.AuthService;
import com.aiims.gdms.util.AESGCMDecryption;

import jakarta.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;



@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AuthResponse> register(@ModelAttribute AuthRequest request) {

        System.out.println("Controller hit...");

        try {
            String decryptedUsername = AESGCMDecryption.decrypt(request.getUsername());
            
            String decryptedPassword = AESGCMDecryption.decrypt(request.getPassword());
            String decryptedPhoneNumber = AESGCMDecryption.decrypt(request.getPhoneNumber());

            request.setUsername(decryptedUsername);
            request.setPassword(decryptedPassword);
            request.setPhoneNumber(decryptedPhoneNumber);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new AuthResponse("Decryption failed: " + e.getMessage()));
        }

        String photoPath = null;
        MultipartFile photo = request.getPhoto();

        if (photo != null && !photo.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename().replaceAll("\\s+", "_");
                Path uploadPath = Paths.get("src/main/resources/static/uploads");

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                photoPath = "uploads/" + fileName;

            } catch (IOException e) {
                return ResponseEntity.internalServerError().body(new AuthResponse("Error uploading photo: " + e.getMessage()));
            }
        }

        AuthResponse response = authService.register(request, photoPath);

        if (response.getMessage() != null && response.getMessage().contains("already exists")) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        try {
            String decryptedUsername = AESGCMDecryption.decrypt(request.getUsername());
            String decryptedPassword = AESGCMDecryption.decrypt(request.getPassword());

            request.setUsername(decryptedUsername);
            request.setPassword(decryptedPassword);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new AuthResponse("Decryption failed: " + e.getMessage()));
        }

        AuthResponse response = authService.login(request);

        if (response.getMessage() != null && response.getMessage().contains("Invalid")) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }


} 