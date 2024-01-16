package com.exe.inventorymsystemserver.Service.impl;

import com.exe.inventorymsystemserver.Exception.DuplicateMachineModelException;
import com.exe.inventorymsystemserver.Exception.InvalidMachineModelException;
import com.exe.inventorymsystemserver.Exception.InvalidMachineTypeException;
import com.exe.inventorymsystemserver.Exception.ItemAttachToModelTypeException;
import com.exe.inventorymsystemserver.Model.MachineModel;
import com.exe.inventorymsystemserver.Model.MachineType;
import com.exe.inventorymsystemserver.Repository.IMachineModelRepository;
import com.exe.inventorymsystemserver.Repository.IMachineTypeRepository;
import com.exe.inventorymsystemserver.Service.IMachineModelService;
import com.exe.inventorymsystemserver.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MachineModelService implements IMachineModelService {

    private final IMachineModelRepository machineModelRepository;
    private final IMachineTypeRepository machineTypeRepository;
    private final JwtUtil jwtUtil;
    private final Path fileStoragePath;
    private final FileStorageService fileStorageService;

    @Autowired
    public MachineModelService(@Value("${spring.servlet.multipart.location:temp}")String fileStorageLocation,
                               FileStorageService fileStorageService,
                               IMachineModelRepository machineModelRepository,
                               IMachineTypeRepository machineTypeRepository,
                               JwtUtil jwtUtil) throws java.io.IOException {

        this.machineModelRepository = machineModelRepository;
        this.machineTypeRepository = machineTypeRepository;
        this.jwtUtil = jwtUtil;
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageService =  fileStorageService;
        Files.createDirectories(fileStoragePath);
    }

    public MachineModel createOrUpdateMachineModel(MachineModel machineModel, MultipartFile pdfFile, String jwtToken){

        // Extract the username from the JWT token
        String username = jwtUtil.extractUsername(jwtToken);

        // Check if machineModel has a non-null modelId if not then UPDATE
        if (machineModel.getModelId() == null){
            // Creating a new Machine Model
            if (machineModel.getMachineModelNumber() == null){
                // Throw exception if machineModelNumber is null
                throw new InvalidMachineModelException("Machine Model Number Cannot Be Null");
            }

            if (machineModel.getMachineType() == null){
                // Throw exception if machineModelNumber is null
                throw new InvalidMachineModelException("Machine Type Cannot Be Null");
            }

            // Check for Duplicate Machine Model Number
            if (machineModelRepository.existsByMachineModelNumber(machineModel.getMachineModelNumber())) {
                throw new DuplicateMachineModelException("Machine Model With Same Number Already Exists");
            }

            // Check if machineType exists
            MachineType machineType = getMachineTypeById(machineModel.getMachineType().getMachineTypeId());

            // Creating a new MachineModel
            machineModel.setCreatedBy(username);
            machineModel.setCreatedDate(LocalDateTime.now());
            machineModel.setMachineType(machineType);
            machineModel.setStatus(true);

            // New Fields
            machineModel.setMachineTypeId(machineType.getMachineTypeId());
            machineModel.setMachineTypeName(machineType.getMachineTypeName());

            // Handle Pdf upload
            if (pdfFile != null && !pdfFile.isEmpty()){
                // Save or Process the pdf file and update the pdf location in machinemodel entity
                String pdflocation = fileStorageService.storeFile(pdfFile);
                machineModel.setPdfLocation(pdflocation);
            }

        } else {

            // Updating an existing MachineModel
            // Retrieve the existing MachineModel from the database based on the modelId
            Optional<MachineModel> existingMachineModelOptional = machineModelRepository.findById(machineModel.getModelId());

            if (existingMachineModelOptional.isPresent()){
                // Updating an Existing MachineModel
                if (machineModel.getMachineModelNumber() == null) {
                    // Throw exception if machineModelNumber is null
                    throw new InvalidMachineModelException("Machine Model Number Cannot Be Null");
                }

                if (machineModel.getMachineType() == null){
                    // Throw exception if machineModelNumber is null
                    throw new InvalidMachineModelException("Machine Type Cannot Be Null");
                }

                // Check for Duplicate Machine Model Number
                if (machineModelRepository.existsByMachineModelNumberAndModelIdNot(machineModel.getMachineModelNumber(), machineModel.getModelId())) {
                    throw new DuplicateMachineModelException("Machine Model With Same Number Already Exists");
                }

                // Check if machineType exists
                MachineType machineType = getMachineTypeById(machineModel.getMachineType().getMachineTypeId());

                // Update Only the necessary Fields
                MachineModel existingMachineModel = existingMachineModelOptional.get();
                existingMachineModel.setModifyBy(username);
                existingMachineModel.setModifyDate(LocalDateTime.now());
                existingMachineModel.setMachineModelNumber(machineModel.getMachineModelNumber());
                existingMachineModel.setMachineType(machineType);

                // New Fields
                existingMachineModel.setMachineTypeId(machineType.getMachineTypeId());
                existingMachineModel.setMachineTypeName(machineType.getMachineTypeName());

                // Handle Pdf upload
                if (pdfFile != null && !pdfFile.isEmpty()){
                    // Save or Process the pdf file and update the pdf location in machinemodel entity
                    String pdflocation = fileStorageService.storeFile(pdfFile);
                    existingMachineModel.setPdfLocation(pdflocation);
                }

                // Save the updated entity back to the database

                return machineModelRepository.save(existingMachineModel);
            } else {
                // Handle the case where the entity with the given ID is not found
                // You can throw an exception or handle it based on your requirements
                throw new InvalidMachineModelException("Machine Model with ID " + machineModel.getModelId() + " not found.");
            }
        }

        // Save the new or updated MachineModel to the database
        return machineModelRepository.save(machineModel);
    }

    // Helper method to get MachineType by ID
    private MachineType getMachineTypeById(Long machineTypeId) {
        return machineTypeRepository.findById(machineTypeId)
                .orElseThrow(() -> new InvalidMachineTypeException("Machine Type with ID " + machineTypeId + " not found."));
    }

    // Method to get ALL Machine Models
    public List<MachineModel> getAllMachineModels() {

        List<MachineModel> machineModelList = machineModelRepository.findAll();

        for(MachineModel machineModel : machineModelList){
            // Update Pdf URLs
            machineModel.setPdfLocation(getPdfUrl(machineModel.getPdfLocation()));
        }
        return machineModelList;
    }

    // Method to get ALL Machine Models Names
    public List<String> getAllMachineModelNames(){
        List<MachineModel> machineModels = machineModelRepository.findAll();
        return machineModels.stream()
                .map(MachineModel::getMachineModelNumber)
                .collect(Collectors.toList());
    }

    // Delete Method
    public MachineModel deleteMachineModel(Long modelId) {
        // Check if the machine model exists
        MachineModel machineModel = machineModelRepository.findById(modelId)
                .orElseThrow(() -> new InvalidMachineModelException("Machine Model not found with ID: " + modelId));

        try {
            // If it exists, delete the machine model
            machineModelRepository.deleteById(modelId);
        } catch (DataIntegrityViolationException ex) {
            // Catch DataIntegrityViolationException and throw a more specific exception
            throw new ItemAttachToModelTypeException("Cannot delete machine model with ID: " + modelId +
                    " due to existing references in other tables.");
        }
        return machineModel;
    }

    // Get Machine Model By ID
    @Override
    public MachineModel getMachineModelById(Long modelId){
        Optional<MachineModel> machineModelOptional = machineModelRepository.findById(modelId);

        if (machineModelOptional.isPresent()){
            machineModelOptional.ifPresent(machineModel -> {
                machineModel.setPdfLocation(getPdfUrl(machineModel.getPdfLocation()));
            });
            return machineModelOptional.get();
        }else {
            throw new InvalidMachineModelException("Machine Model with ID " + modelId + " not found.");
        }
    }

    // Helper method to get the full pdf URL
    private String getPdfUrl(String pdfFileName) {
        // Assuming you have a method in FileStorageService to generate the full URL
        return fileStorageService.getFileUrl(pdfFileName);
    }
}
