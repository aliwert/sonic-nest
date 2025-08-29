package com.aliwert.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    @Value("${app.upload.video-dir}")
    private String videoUploadDir;
    
    @Value("${app.upload.thumbnail-dir}")
    private String thumbnailUploadDir;
    
    public String uploadVideo(MultipartFile videoFile) throws IOException {
        // video file validation
        if (!isValidVideoFile(videoFile)) {
            throw new IllegalArgumentException("Invalid video format");
        }
        
        String fileName = generateFileName(videoFile.getOriginalFilename());
        Path uploadPath = Paths.get(videoUploadDir);
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(videoFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        return fileName;
    }
    
    public String uploadThumbnail(MultipartFile thumbnailFile) throws IOException {
         if (!isValidImageFile(thumbnailFile)) {
        throw new IllegalArgumentException("Invalid image format");
    }
    
    String fileName = generateFileName(thumbnailFile.getOriginalFilename());
    Path uploadPath = Paths.get(thumbnailUploadDir);
    
    if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
    }
    
    Path filePath = uploadPath.resolve(fileName);
    Files.copy(thumbnailFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    
    return fileName;
    }

    private boolean isValidImageFile(MultipartFile file) {
    String contentType = file.getContentType();
    return contentType != null && 
           (contentType.equals("image/jpeg") || 
            contentType.equals("image/png") || 
            contentType.equals("image/jpg") ||
            contentType.equals("image/webp"));
}
    
    private boolean isValidVideoFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && 
               (contentType.startsWith("video/") || 
                contentType.equals("application/octet-stream"));
    }
    
    private String generateFileName(String originalFilename) {
        return UUID.randomUUID().toString() + "_" + originalFilename;
    }
}
