package com.exe.inventorymsystemserver.Service;

import com.exe.inventorymsystemserver.Dto.LocationDTO;
import com.exe.inventorymsystemserver.Model.Location;

import java.util.List;
import java.util.Map;

public interface ILocationService {
    Location createOrUpdateLocationModel(Location location, String jwtToken);

    Map<String, List<LocationDTO>> getLocationListsByTypes();


}
