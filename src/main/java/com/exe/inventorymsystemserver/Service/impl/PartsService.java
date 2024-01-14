package com.exe.inventorymsystemserver.Service.impl;

import com.exe.inventorymsystemserver.Dto.PartMachineModelDTO;
import com.exe.inventorymsystemserver.Exception.DuplicatePartNumberException;
import com.exe.inventorymsystemserver.Exception.InvalidPartException;
import com.exe.inventorymsystemserver.Model.MachineModel;
import com.exe.inventorymsystemserver.Model.Parts;
import com.exe.inventorymsystemserver.Repository.IMachineModelRepository;
import com.exe.inventorymsystemserver.Repository.IPartsRepository;
import com.exe.inventorymsystemserver.Service.IPartsService;
import com.exe.inventorymsystemserver.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartsService implements IPartsService {

    private final IPartsRepository partsRepository;
    private final IMachineModelRepository machineModelRepository;
    private final JwtUtil jwtUtil;
    private final Path fileStoragePath;
    private final FileStorageService fileStorageService;

    @Autowired
    public PartsService(@Value("${spring.servlet.multipart.location:temp}") String fileStorageLocation,
                        IPartsRepository partsRepository, IMachineModelRepository machineModelRepository,
                        FileStorageService fileStorageService,
                        JwtUtil jwtUtil) throws Exception {

        this.partsRepository = partsRepository;
        this.machineModelRepository = machineModelRepository;
        this.jwtUtil = jwtUtil;
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageService =  fileStorageService;
        Files.createDirectories(fileStoragePath);
    }

    public Parts createOrUpdatePart(Parts parts, String jwtToken, List<Long>machineModelIds,
                                    MultipartFile imageFile1, MultipartFile imageFile2) {

        // Extract the username from the JWT token
        String username = jwtUtil.extractUsername(jwtToken);

        // Check if parts has a non-null partId to determine whether it's a new parts or an update
        if (parts.getPartId() == null) {
            // Creating a new parts
            if (parts.getPartNumber() == null){
                throw new InvalidPartException("Part Number Cannot Be Null");
            }

            if (parts.getQuantity() == 0){
                throw new InvalidPartException("Part Quantity Cannot Be Null");
            }

            if (parts.getPrice() == 0){
                throw new InvalidPartException("Part Price Cannot Be Null");
            }

            if (partsRepository.existsByPartNumber(parts.getPartNumber())){
                throw new DuplicatePartNumberException("Part Number with Same Number Already Exists");
            }

            // Set associated machine models
            List<MachineModel> machineModels = machineModelRepository.findAllById(machineModelIds);
            parts.setMachineModels(machineModels);

            // Set the createdBy, createdDate, and status for a new part
            parts.setCreatedBy(username);
            parts.setCreatedDate(LocalDateTime.now());
            parts.setStatus(true);

            // Handle image uploads
            if (imageFile1 != null && !imageFile1.isEmpty()) {
                // Save or process the image and update the image location in the Parts entity
                String image1Loc = fileStorageService.storeFile(imageFile1);
                parts.setImage1Loc(image1Loc);
            }

            if (imageFile2 != null && !imageFile2.isEmpty()) {
                // Save or process the image and update the image location in the Parts entity
                String image2Loc = fileStorageService.storeFile(imageFile2);
                parts.setImage2Loc(image2Loc);
            }

            // Save the new parts
            return partsRepository.save(parts);
        } else {
            // Updating an existing parts
            Optional<Parts> existingPart = partsRepository.findById(parts.getPartId());

            if (existingPart.isPresent()) {

                if (parts.getPartNumber() == null){
                    throw new InvalidPartException("Part Number Cannot Be Null");
                }

                if (parts.getQuantity() == 0){
                    throw new InvalidPartException("Part Quantity Cannot Be Null");
                }

                if (parts.getPrice() == 0){
                    throw new InvalidPartException("Part Price Cannot Be Null");
                }

                if (partsRepository.existsByPartNumberAndPartIdNot(parts.getPartNumber(),parts.getPartId())){
                    throw new DuplicatePartNumberException("Part Number with Same Number Already Exists");
                }

                // Update only the necessary fields
                Parts updatePart = existingPart.get();
                updatePart.setPartName(parts.getPartName());
                updatePart.setDescription(parts.getDescription());
                updatePart.setQuantity(parts.getQuantity());
                updatePart.setPrice(parts.getPrice());
                updatePart.setColorCode(parts.getColorCode());
                updatePart.setModifyBy(username);
                updatePart.setModifyDate(LocalDateTime.now());
                // Update other fields as needed

                // Handle image uploads for updates
                if (imageFile1 != null && !imageFile1.isEmpty()) {
                    // Save or process the image and update the image location in the Parts entity
                    String image1Loc = fileStorageService.storeFile(imageFile1);
                    updatePart.setImage1Loc(image1Loc);
                }

                if (imageFile2 != null && !imageFile2.isEmpty()) {
                    // Save or process the image and update the image location in the Parts entity
                    String image2Loc = fileStorageService.storeFile(imageFile2);
                    updatePart.setImage2Loc(image2Loc);
                }

                // Save the updated parts back to the database
                return partsRepository.save(updatePart);
            } else {
                throw new InvalidPartException("Part ID " + parts.getPartId() + " not found.");
            }
        }
    }

    // Method to Get All Parts
    /*public List<Parts> getAllPartModels(){
        List<Parts> partsList = partsRepository.findByStatus(true);

        // Update image URLs in each part
        for (Parts part : partsList) {
            part.setImage1Loc(getImageUrl(part.getImage1Loc()));
            part.setImage2Loc(getImageUrl(part.getImage2Loc()));
        }
        //return partsRepository.findByStatus(true);
        return partsList;
    }*/

    // Method to Get All Parts along with their models
    public List<Parts> getAllPartModels() {
        List<Parts> partsList = partsRepository.findByStatus(true);

        // Update image URLs and include associated MachineModelDTOs in each part
        for (Parts part : partsList) {
            // Update image URLs
            part.setImage1Loc(getImageUrl(part.getImage1Loc()));
            part.setImage2Loc(getImageUrl(part.getImage2Loc()));

            // Include associated MachineModelDTOs
            List<PartMachineModelDTO> machineModelDTOList = part.getMachineModels().stream()
                    .map(this::convertToMachineModelDTO)
                    .collect(Collectors.toList());

            // Set the MachineModelDTOs within the Parts entity
            part.setMachineModelsDTO(machineModelDTOList);
        }
        return partsList;
    }

    private PartMachineModelDTO convertToMachineModelDTO(MachineModel machineModel) {
        PartMachineModelDTO dto = new PartMachineModelDTO();
        dto.setModelId(machineModel.getModelId());
        dto.setMachineModelNumber(machineModel.getMachineModelNumber());
        dto.setPdfLocation(getPdfUrl(machineModel.getPdfLocation()));
        dto.setMachineTypeId(machineModel.getMachineTypeId());
        dto.setMachineTypeName(machineModel.getMachineTypeName());
        // Set other properties as needed

        return dto;
    }

    // Helper method to get the full image URL
    private String getImageUrl(String imageFileName) {
        // Assuming you have a method in FileStorageService to generate the full URL
        return fileStorageService.getFileUrl(imageFileName);
    }

    // Helper method to get the full pdf URL
    private String getPdfUrl(String pdfFileName) {
        // Assuming you have a method in FileStorageService to generate the full URL
        return fileStorageService.getFileUrl(pdfFileName);
    }

    // Delete Part
    @Override
    @Transactional
    public void deleteItem(Long partId){
        partsRepository.deletePart(partId);
    }

    // Get Item By Id OLD
    /*@Override
    public Optional<Parts> getPartById(Long partId){
        return partsRepository.findByPartIdAndStatus(partId, true);
    }*/

    // Get Item By Id NEW
    @Override
    public Optional<Parts> getPartById(Long partId) {
        Optional<Parts> optionalPart = partsRepository.findByPartIdAndStatus(partId, true);

        optionalPart.ifPresent(part -> {
            // Update image URLs
            part.setImage1Loc(getImageUrl(part.getImage1Loc()));
            part.setImage2Loc(getImageUrl(part.getImage2Loc()));

            // Include associated MachineModelDTOs
            List<PartMachineModelDTO> machineModelDTOList = part.getMachineModels().stream()
                    .map(this::convertToMachineModelDTO)
                    .collect(Collectors.toList());

            // Set the MachineModelDTOs within the Parts entity
            part.setMachineModelsDTO(machineModelDTOList);
        });

        return optionalPart;
    }

    // Get LOW quantity Parts
    /*public List<Parts> getAllPartsBelowLimitQuantity(){
        return partsRepository.findByQuantityLessThanAndLimitQuantityIsNotNull(Integer.MAX_VALUE);
    }*/

    public List<Parts> getAllPartsBelowLimitQuantity() {
        List<Parts> allParts = partsRepository.findByStatus(true);
        return filterPartsByQuantityBelowLimit(allParts);
    }

    private List<Parts> filterPartsByQuantityBelowLimit(List<Parts> parts) {
        return parts.stream()
                .filter(part -> part.getQuantity() < part.getLimitQuantity())
                .collect(Collectors.toList());
    }

}
