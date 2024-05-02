package com.example.crudapp.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    public FileStorageService() {
        this.fileStorageLocation = Paths.get("uploads");
    }

    public void storeFile(MultipartFile file, String fileName) {
        try {
            Path targetFile = this.fileStorageLocation.resolve(fileName).normalize().toAbsolutePath();
            InputStream stream = file.getInputStream();
            Files.copy(stream, targetFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Couldn't store file " + fileName);
            e.printStackTrace();
        }
    }

    public void storeFile(MultipartFile file) {
        this.storeFile(file, file.getOriginalFilename());
    }

    public Resource loadFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!Files.exists(filePath)) {
                return null;
            }
            return resource;
        } catch (IOException e){
            System.err.println("Couldn't load file " + fileName);
            e.printStackTrace();
        }
        return null;
    }

    public String getContentType(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            return Files.probeContentType(filePath);
        } catch (IOException e) {
            System.err.println("Couldn't load file " + fileName);
            e.printStackTrace();
        }
        return null;
    }
}
