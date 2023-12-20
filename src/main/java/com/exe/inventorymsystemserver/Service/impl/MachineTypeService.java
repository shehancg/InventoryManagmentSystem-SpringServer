package com.exe.inventorymsystemserver.Service.impl;

import com.exe.inventorymsystemserver.Exception.DuplicateMachineTypeException;
import com.exe.inventorymsystemserver.Exception.InvalidMachineTypeException;
import com.exe.inventorymsystemserver.Model.MachineType;
import com.exe.inventorymsystemserver.Repository.IMachineTypeRepository;
import com.exe.inventorymsystemserver.Service.IMachineTypeService;
import com.exe.inventorymsystemserver.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MachineTypeService implements IMachineTypeService {

    private final IMachineTypeRepository machineTypeRepository;
    private final JwtUtil jwtUtil;

    @Override
    public MachineType createOrUpdateMachineType(MachineType machineType, String jwtToken) {
        // Extract the username from the JWT token
        String username = jwtUtil.extractUsername(jwtToken);

        // Check if machineType has a non-null machineTypeId
        if (machineType.getMachineTypeId() == null) {

            // Creating a new MachineType
            if (machineType.getMachineTypeName() == null){
                // Throw exception if machineTypeName is null
                throw new InvalidMachineTypeException("Machine Type Name Cannot Be Null");
            }

            // Check for Duplicate Machine Type Name
            if (machineTypeRepository.existsByMachineTypeName(machineType.getMachineTypeName())){
                throw new DuplicateMachineTypeException("Machine Type With Same Name Already Exists");
            }

            // Creating a new MachineType
            machineType.setCreatedBy(username);
            machineType.setCreatedDate(LocalDateTime.now());
            machineType.setStatus(true);
        } else {
            // Updating an existing MachineType
            // Retrieve the existing MachineType from the database based on the machineTypeId
            Optional<MachineType> existingMachineTypeOptional = machineTypeRepository.findById(machineType.getMachineTypeId());

            if (existingMachineTypeOptional.isPresent()) {

                // Updating a Existing MachineType
                if (machineType.getMachineTypeName() == null){
                    // Throw exception if machineTypeName is null
                    throw new InvalidMachineTypeException("Machine Type Name Cannot Be Null");
                }

                // Check for Duplicate Machine Type Name
                if (machineTypeRepository.existsByMachineTypeName(machineType.getMachineTypeName())){
                    throw new DuplicateMachineTypeException("Machine Type With Same Name Already Exists");
                }

                // Update only the necessary fields
                MachineType existingMachineType = existingMachineTypeOptional.get();
                existingMachineType.setModifyBy(username);
                existingMachineType.setModifyDate(LocalDateTime.now());
                existingMachineType.setMachineTypeName(machineType.getMachineTypeName());

                // Save the updated entity back to the database
                return machineTypeRepository.save(existingMachineType);
            } else {
                // Handle the case where the entity with the given ID is not found
                // You can throw an exception or handle it based on your requirements
                return null;
            }
        }

        // Save the new or updated MachineType to the database
        return machineTypeRepository.save(machineType);
    }

    public List<MachineType> getAllMachineTypes() {
        return machineTypeRepository.findAll();
    }

    public List<String> getAllMachineTypeNames(){
        List<MachineType> machineTypes = machineTypeRepository.findAll();
        return machineTypes.stream()
                .map(MachineType::getMachineTypeName)
                .collect(Collectors.toList());
    }
}
