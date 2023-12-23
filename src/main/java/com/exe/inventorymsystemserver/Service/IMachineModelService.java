package com.exe.inventorymsystemserver.Service;

import com.exe.inventorymsystemserver.Model.MachineModel;

import java.util.List;

public interface IMachineModelService {
    MachineModel createOrUpdateMachineModel(MachineModel machineModel, String jwtToken);

    List<MachineModel> getAllMachineModels();

    List<String> getAllMachineModelNames();

    MachineModel deleteMachineModel(Long modelId);
}
