package com.exe.inventorymsystemserver.Service;

import com.exe.inventorymsystemserver.Model.MachineModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMachineModelService {
    MachineModel createOrUpdateMachineModel(MachineModel machineModel, MultipartFile pdfFile, String jwtToken);

    List<MachineModel> getAllMachineModels();

    List<String> getAllMachineModelNames();

    MachineModel deleteMachineModel(Long modelId);

    // Get Machine Model By ID
    MachineModel getMachineModelById(Long machineModelId);
}
