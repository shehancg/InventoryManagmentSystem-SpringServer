package com.exe.inventorymsystemserver.Model;

import com.exe.inventorymsystemserver.Dto.PartMachineModelDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "parts")
@Data
public class Parts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private Long partId;

    @Column(name = "part_number", unique = true, nullable = false)
    private String partNumber;

    @Column(name = "part_name")
    private String partName;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "limit_quantity")
    private int limitQuantity;

    @Column(name = "price")
    private double price;

    @Column(name = "l1")
    private String location1;

    @Column(name = "l2")
    private String location2;

    @Column(name = "l3")
    private String location3;

    @Column(name = "l1_name")
    private String location1Name;

    @Column(name = "l2_name")
    private String location2Name;

    @Column(name = "l3_name")
    private String location3Name;

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

    @Column(name = "image1_loc")
    private String image1Loc;

    @Transient
    @JsonIgnore
    private MultipartFile imageFile1; // Transient field to handle file upload

    @Column(name = "image2_loc")
    private String image2Loc;

    @Transient
    @JsonIgnore
    private MultipartFile imageFile2; // Transient field to handle file upload

    @Column(name = "color_code")
    private String colorCode;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonBackReference
    @JoinTable(
            name = "machine_model_part",
            joinColumns = @JoinColumn(name = "part_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"part_id", "model_id"})
    )
    private List<MachineModel> machineModels;

    @Transient
    private List<PartMachineModelDTO> machineModelsDTO;

    @OneToMany(mappedBy = "part",fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonManagedReference
    private List<Transaction> transactions;

    public Parts(Long partId, String partNumber, String partName, String description, int quantity, int limitQuantity, double price, String location1, String location2, String location3, String location1Name, String location2Name, String location3Name, String createdBy, LocalDateTime createdDate, String modifyBy, LocalDateTime modifyDate, boolean status, String image1Loc, MultipartFile imageFile1, String image2Loc, MultipartFile imageFile2, String colorCode, List<MachineModel> machineModels, List<PartMachineModelDTO> machineModelsDTO, List<Transaction> transactions) {
        this.partId = partId;
        this.partNumber = partNumber;
        this.partName = partName;
        this.description = description;
        this.quantity = quantity;
        this.limitQuantity = limitQuantity;
        this.price = price;
        this.location1 = location1;
        this.location2 = location2;
        this.location3 = location3;
        this.location1Name = location1Name;
        this.location2Name = location2Name;
        this.location3Name = location3Name;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifyBy = modifyBy;
        this.modifyDate = modifyDate;
        this.status = status;
        this.image1Loc = image1Loc;
        this.imageFile1 = imageFile1;
        this.image2Loc = image2Loc;
        this.imageFile2 = imageFile2;
        this.colorCode = colorCode;
        this.machineModels = machineModels;
        this.machineModelsDTO = machineModelsDTO;
        this.transactions = transactions;
    }

    public Parts() {

    }
}
