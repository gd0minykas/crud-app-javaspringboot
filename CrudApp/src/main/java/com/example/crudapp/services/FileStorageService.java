package com.example.crudapp.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileLocation;

    public FileStorageService() {
        this.fileLocation = Paths.get("uploads");
    }

    public void store(MultipartFile file, String fileName) {
        try {
            Path targetFile = this.fileLocation.resolve(fileName).normalize().toAbsolutePath();
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, targetFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("An error occurred while storing the file");
            e.printStackTrace();
        }
    }

    public void store(MultipartFile file) {
        store(file, "uploads/" + file.getOriginalFilename());
    }

    public Resource load(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!Files.exists(filePath)) { return null; }
            return resource;
        } catch (MalformedURLException e) {
            System.out.println("An error occurred while loading the file");
            e.printStackTrace();
        }
        return null;
    }

    public String GetContentType(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            return Files.probeContentType(filePath);
        } catch (IOException e) {
            System.out.println("An error occurred while loading the file");
            e.printStackTrace();
        }
        return null;
    }

    public void delete(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.out.println("An error occurred while deleting the file");
            e.printStackTrace();
        }
    }

}
