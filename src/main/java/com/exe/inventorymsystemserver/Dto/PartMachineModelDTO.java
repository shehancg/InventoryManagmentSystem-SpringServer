package com.exe.inventorymsystemserver.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PartMachineModelDTO {
    private Long modelId;
    private String machineModelNumber;
    private String pdfLocation;

    public PartMachineModelDTO(Long modelId, String machineModelNumber, String pdfLocation) {
        this.modelId = modelId;
        this.machineModelNumber = machineModelNumber;
        this.pdfLocation = pdfLocation;
    }

    public PartMachineModelDTO() {

    }
}
