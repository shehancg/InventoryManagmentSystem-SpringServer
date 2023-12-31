package com.exe.inventorymsystemserver.Service.impl;

import com.exe.inventorymsystemserver.Exception.InvalidPartException;
import com.exe.inventorymsystemserver.Model.Parts;
import com.exe.inventorymsystemserver.Repository.IPartsRepository;
import com.exe.inventorymsystemserver.Service.IPartsService;
import com.exe.inventorymsystemserver.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class PartsService implements IPartsService {

    private final IPartsRepository partsRepository;
    private final JwtUtil jwtUtil;
    private final Path fileStoragePath;

    @Autowired
    public PartsService(@Value("${spring.servlet.multipart.location:temp}") String fileStorageLocation,
                        IPartsRepository partsRepository,
                        JwtUtil jwtUtil) throws Exception {

        this.partsRepository = partsRepository;
        this.jwtUtil = jwtUtil;
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        Files.createDirectories(fileStoragePath);
    }

    public Parts createOrUpdatePart(Parts part, String jwtToken) {

        // Extract the username from the JWT token
        String username = jwtUtil.extractUsername(jwtToken);

        // Check if part has a non-null partId to determine whether it's a new part or an update
        if (part.getPartId() == null) {
            // Creating a new part
            // Additional validation checks can be added

            // Save the new part
            return partsRepository.save(part);
        } else {
            // Updating an existing part
            Optional<Parts> existingPart = partsRepository.findById(part.getPartId());

            if (existingPart.isPresent()) {
                // Update only the necessary fields
                Parts updatePart = existingPart.get();
                updatePart.setPartName(part.getPartName());
                updatePart.setDescription(part.getDescription());
                updatePart.setQuantity(part.getQuantity());
                updatePart.setPrice(part.getPrice());
                updatePart.setColorCode(part.getColorCode());
                // Update other fields as needed

                // Save the updated part back to the database
                return partsRepository.save(updatePart);
            } else {
                throw new InvalidPartException("Part ID " + part.getPartId() + " not found.");
            }
        }
    }


}
