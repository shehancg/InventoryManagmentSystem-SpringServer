package com.exe.inventorymsystemserver.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "machine_model")
@Data
public class MachineModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private Long modelId;

    @Column(name = "machine_model_number", unique = true, nullable = false)
    private String machineModelNumber;

    @Column(name = "pdf_location")
    private String pdfLocation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "machine_type_id")
    private MachineType machineType;

    @Transient
    @JsonIgnore
    private MultipartFile pdfFile; // Transient field to handle file upload

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

    @ManyToMany(mappedBy = "machineModels", fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonManagedReference
    private List<Parts> parts;

    public MachineModel(Long modelId, String machineModelNumber, String pdfLocation, MachineType machineType, MultipartFile pdfFile, String createdBy, LocalDateTime createdDate, String modifyBy, LocalDateTime modifyDate, Boolean status) {
        this.modelId = modelId;
        this.machineModelNumber = machineModelNumber;
        this.pdfLocation = pdfLocation;
        this.machineType = machineType;
        this.pdfFile = pdfFile;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifyBy = modifyBy;
        this.modifyDate = modifyDate;
        this.status = status;
    }

    public MachineModel() {
    }
}
