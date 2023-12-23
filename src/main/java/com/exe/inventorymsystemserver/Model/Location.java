package com.exe.inventorymsystemserver.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "location")
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "location_name", unique = true, nullable = false)
    private String locationName;

    @Column(name = "location_type", nullable = false)
    private String locationType;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modify_by")
    private String modifyBy;

    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    public Location(Long locationId, String locationName, String locationType, String createdBy, LocalDateTime createdDate, String modifyBy, LocalDateTime modifyDate, boolean status) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.locationType = locationType;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifyBy = modifyBy;
        this.modifyDate = modifyDate;
        this.status = status;
    }


}