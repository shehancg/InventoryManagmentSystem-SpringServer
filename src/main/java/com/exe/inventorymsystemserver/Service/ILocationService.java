package com.exe.inventorymsystemserver.Service;

import com.exe.inventorymsystemserver.Model.Location;

public interface ILocationService {
    Location createOrUpdateLocationModel(Location location, String jwtToken);
}
