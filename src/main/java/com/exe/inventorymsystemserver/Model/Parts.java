package com.exe.inventorymsystemserver.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "price")
    private double price;

    @Column(name = "l1")
    private String location1;

    @Column(name = "l2")
    private String location2;

    @Column(name = "l3")
    private String location3;

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
    private MultipartFile imageFile11; // Transient field to handle file upload

    @Column(name = "image2_loc")
    private String image2Loc;

    @Transient
    @JsonIgnore
    private MultipartFile imageFile2; // Transient field to handle file upload

    @Column(name = "color_code")
    private String colorCode;

    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "machine_model_part",
            joinColumns = @JoinColumn(name = "part_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"part_id", "model_id"})
    )
    private List<MachineModel> machineModels;

}
