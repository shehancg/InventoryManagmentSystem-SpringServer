package com.exe.inventorymsystemserver.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PartMachineModelDTO {
    private Long modelId;
    private String machineModelNumber;
    private String pdfLocation;
    private Long machineTypeId;
    private String machineTypeName;

    public PartMachineModelDTO(Long modelId, String machineModelNumber, String pdfLocation, Long machineTypeId, String machineTypeName) {
        this.modelId = modelId;
        this.machineModelNumber = machineModelNumber;
        this.pdfLocation = pdfLocation;
        this.machineTypeId = machineTypeId;
        this.machineTypeName = machineTypeName;
    }

    public PartMachineModelDTO() {

    }
}
