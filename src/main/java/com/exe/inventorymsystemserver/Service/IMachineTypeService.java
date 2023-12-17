package com.exe.inventorymsystemserver.Service;

import com.exe.inventorymsystemserver.Model.MachineType;

public interface IMachineTypeService {
    MachineType createOrUpdateMachineType(MachineType machineType, String jwtToken);
}
