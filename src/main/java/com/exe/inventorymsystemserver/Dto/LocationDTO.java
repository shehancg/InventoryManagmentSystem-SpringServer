package com.exe.inventorymsystemserver.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LocationDTO {
    private Long locationId;
    private String locationName;

    public LocationDTO(Long locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    public LocationDTO() {
    }
}