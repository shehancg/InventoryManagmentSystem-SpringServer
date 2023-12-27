package com.exe.inventorymsystemserver.Service;

import com.exe.inventorymsystemserver.Dto.LocationDTO;
import com.exe.inventorymsystemserver.Model.Location;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ILocationService {
    Location createOrUpdateLocationModel(Location location, String jwtToken);

    Map<String, List<LocationDTO>> getLocationListsByTypes();
}
