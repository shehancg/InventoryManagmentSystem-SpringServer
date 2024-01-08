package com.exe.inventorymsystemserver.Service.impl;

import com.exe.inventorymsystemserver.Exception.UnknownException;
import com.exe.inventorymsystemserver.Service.IFileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService implements IFileStorageService {


    @Override
    public Resource downloadFile(String fileName) {
        Path path = Paths.get("src/main/resources/static").resolve(fileName);
        Resource resource;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new UnknownException(e.getMessage() + " ⚠⚠⚠");
        }

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new UnknownException("The file doesn't exist or is not readable ⚠⚠⚠");
        }
    }

    public String storeFile(MultipartFile file) {
        try {
            // Generate a unique file name
            String fileName = generateFileName(file.getOriginalFilename());

            // Prepare the file path
            Path targetLocation = Paths.get("src/main/resources/static").resolve(fileName);

            // Copy the file to the target location
            Files.copy(file.getInputStream(), targetLocation);

            // Return the file path or location
            return fileName;
        } catch (IOException e) {
            // Handle the exception appropriately (e.g., log it)
            throw new RuntimeException("Failed to store file", e);
        }
    }

    private String generateFileName(String originalFileName) {
        // Generate a unique file name using UUID
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + extension;
    }

    // New method to get the full URL for a file
    public String getFileUrl(String fileName) {
        // Assuming you have a base URL for your image server
        String baseUrl = "http://localhost:8080/";

        // Specify the relative path from the base URL to the directory where images are stored
        String fileStoragePath = "/";

        // Concatenate the base URL, relative path, and file name
        return baseUrl + fileStoragePath + fileName;
    }

    // Method to store a file
    /*public Path storeFile(MultipartFile file, String fileName) throws IOException {
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return targetLocation;
    }*/
}