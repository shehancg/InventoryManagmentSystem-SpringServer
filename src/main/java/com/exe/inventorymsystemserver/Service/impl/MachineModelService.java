package com.exe.inventorymsystemserver.Service.impl;

import com.exe.inventorymsystemserver.Exception.DuplicateMachineModelException;
import com.exe.inventorymsystemserver.Exception.InvalidMachineModelException;
import com.exe.inventorymsystemserver.Exception.InvalidMachineTypeException;
import com.exe.inventorymsystemserver.Model.MachineModel;
import com.exe.inventorymsystemserver.Model.MachineType;
import com.exe.inventorymsystemserver.Repository.IMachineModelRepository;
import com.exe.inventorymsystemserver.Repository.IMachineTypeRepository;
import com.exe.inventorymsystemserver.Service.IMachineModelService;
import com.exe.inventorymsystemserver.Utils.JwtUtil;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MachineModelService implements IMachineModelService {
    @Autowired
    private IMachineModelRepository machineModelRepository;

    @Autowired
    private IMachineTypeRepository machineTypeRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public MachineModel createOrUpdateMachineModel(MachineModel machineModel, String jwtToken){

        // Extract the username from the JWT token
        String username = jwtUtil.extractUsername(jwtToken);

        // Check if machineModel has a non-null modelId if not then UPDATE
        if (machineModel.getModelId() == null){
            // Creating a new Machine Model
            if (machineModel.getMachineModelNumber() == null){
                // Throw exception if machineModelNumber is null
                throw new InvalidMachineModelException("Machine Model Number Cannot Be Null");
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

            // Handle file upload
            handleFileUpload(machineModel);
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

                // Check for Duplicate Machine Model Number
                if (machineModelRepository.existsByMachineModelNumber(machineModel.getMachineModelNumber())) {
                    throw new DuplicateMachineModelException("Machine Model With Same Number Already Exists");
                }

                // Check if machineType exists
                MachineType machineType = getMachineTypeById(machineModel.getMachineType().getMachineTypeId());

                // Update Only the necessary Fields
                MachineModel existingMachineModel = existingMachineModelOptional.get();
                existingMachineModel.setModifyBy(username);
                existingMachineModel.setModifyDate(LocalDateTime.now());
                existingMachineModel.setMachineModelNumber(machineModel.getMachineModelNumber());
                existingMachineModel.setPdfLocation(machineModel.getPdfLocation());
                existingMachineModel.setMachineType(machineType);

                // Save the updated entity back to the database
                MachineModel updatedMachineModel = machineModelRepository.save(existingMachineModel);

                // Handle file upload
                handleFileUpload(updatedMachineModel);

                return updatedMachineModel;
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

    private void handleFileUpload(MachineModel machineModel) {
        if (machineModel.getPdfFile() != null) {
            try {
                // File path with File.separator for cross-platform compatibility
                //String filePath = "D:" + File.separator + "pdf" + File.separator + machineModel.getMachineModelNumber() + ".pdf";

                String filePath = "src/main/resources/pdf/" + machineModel.getMachineModelNumber() + ".pdf";

                //D:\pdf
                // Save the file to the specified path
                // ...

                // For simplicity, just set the file path to the model
                machineModel.setPdfLocation(filePath);
            } catch (IOException e) {
                // Handle IOException (or specific file-related exceptions) here
                e.printStackTrace();
                // You might want to log the exception or return an error response to the user
            }
        }
    }
}
