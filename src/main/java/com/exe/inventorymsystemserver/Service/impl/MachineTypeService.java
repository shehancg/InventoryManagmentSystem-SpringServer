package com.exe.inventorymsystemserver.Service.impl;

import com.exe.inventorymsystemserver.Model.MachineType;
import com.exe.inventorymsystemserver.Repository.IMachineTypeRepository;
import com.exe.inventorymsystemserver.Service.IMachineTypeService;
import com.exe.inventorymsystemserver.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MachineTypeService implements IMachineTypeService {

    private final IMachineTypeRepository machineTypeRepository;
    private final JwtUtil jwtUtil;

    @Override
    public MachineType createOrUpdateMachineType(MachineType machineType, String jwtToken){
        String username = jwtUtil.extractUsername(jwtToken);

        if (machineType.getMachineTypeId() == null) {
            // Creating a new MachineType
            machineType.setCreatedBy(username);
            machineType.setCreatedDate(LocalDateTime.now());
            machineType.setStatus("ACTIVE");
        } else {
            // Updating an existing MachineType
            machineType.setModifyBy(username);
            machineType.setModifyDate(LocalDateTime.now());
        }

        return machineTypeRepository.save(machineType);
    }
}
