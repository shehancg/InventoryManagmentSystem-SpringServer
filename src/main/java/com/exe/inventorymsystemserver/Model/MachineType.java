package com.exe.inventorymsystemserver.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "machine_type")
@Data
public class MachineType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_type_id")
    private Long machineTypeId;

    @Column(name = "machine_type_name")
    private String machineTypeName;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modify_by")
    private String modifyBy;

    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "machineType", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<MachineModel> machineModels;

    public MachineType(Long machineTypeId, String machineTypeName, String createdBy, LocalDateTime createdDate, String modifyBy, LocalDateTime modifyDate, String status) {
        this.machineTypeId = machineTypeId;
        this.machineTypeName = machineTypeName;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifyBy = modifyBy;
        this.modifyDate = modifyDate;
        this.status = Boolean.parseBoolean(status);
    }

    public MachineType() {
        // Default (parameterless) constructor
    }
}
