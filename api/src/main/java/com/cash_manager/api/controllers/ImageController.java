package com.cash_manager.api.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/api/images")
public class ImageController {

    @Value("${upload.path}") // Path to store the uploaded images, set in application.properties
    private String uploadPath;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            // Generate a unique filename to avoid conflicts
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            Path filePath = Paths.get(uploadPath + uniqueFileName);

            // Save the file to the server
            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                fos.write(file.getBytes());
            }

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/images/")
                    .path(uniqueFileName)
                    .toUriString();

            return ResponseEntity.ok(fileDownloadUri);
        } catch (IOException ex) {
            System.out.println(ex);
            return ResponseEntity.status(500).body("Could not upload the file: " + file.getOriginalFilename());
        }
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        try {
            Path imagePath = Paths.get(uploadPath + fileName);
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{fileName:.+}")
    public ResponseEntity<String> deleteImage(@PathVariable String fileName) {
        try {
            Path imagePath = Paths.get(uploadPath + fileName);
            Files.deleteIfExists(imagePath);
            return ResponseEntity.ok("File deleted successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Could not delete the file: " + fileName);
        }
    }
}