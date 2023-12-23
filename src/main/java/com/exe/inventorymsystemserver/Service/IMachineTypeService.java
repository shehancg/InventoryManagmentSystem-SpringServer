package com.exe.inventorymsystemserver.Service;

import com.exe.inventorymsystemserver.Model.MachineType;

import java.util.List;

public interface IMachineTypeService {
    MachineType createOrUpdateMachineType(MachineType machineType, String jwtToken);

    List<MachineType> getAllMachineTypes();

    List<String> getAllMachineTypeNames();

    void deleteMachineType(Long machineTypeId);
}
