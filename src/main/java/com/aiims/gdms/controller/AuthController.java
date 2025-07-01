package com.aiims.gdms.controller;

import com.aiims.gdms.dto.AuthRequest;
import com.aiims.gdms.dto.AuthResponse;
import com.aiims.gdms.service.AuthService;
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
    public ResponseEntity<AuthResponse> register(@Valid @ModelAttribute AuthRequest request) {

    	System.out.println("Controller hit...");
        MultipartFile photo = request.getPhoto();
        
        if (photo == null) {
            System.out.println("Photo is NULL");
        } else if (photo.isEmpty()) {
            System.out.println("Photo is EMPTY");
        } else {
            System.out.println("Photo received: " + photo.getOriginalFilename());
        }
        
        String photoPath = null;

        if (photo != null && !photo.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename().replaceAll("\\s+", "_");
                String directory = "uploads";
                Path uploadPath = Paths.get("src/main/resources/static/" + directory);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                
                System.out.println("File saved to: " + filePath.toAbsolutePath());

                // Relative path stored for serving via URL
                photoPath = directory + "/" + fileName;

            } catch (IOException e) {
                return ResponseEntity.internalServerError().body(
                    new AuthResponse("Error uploading photo: " + e.getMessage())
                );
            }
        }

        AuthResponse response = authService.register(request, photoPath);

        if (response.getMessage() != null && response.getMessage().contains("already exists")) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }


    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        if (response.getMessage() != null && response.getMessage().contains("Invalid")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
} 