package com.exe.inventorymsystemserver.Service.impl;

import com.exe.inventorymsystemserver.Model.Location;
import com.exe.inventorymsystemserver.Service.ILocationService;
import com.exe.inventorymsystemserver.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements ILocationService {

    @Autowired
    private JwtUtil jwtUtil;

    public Location createorUpdateLocationModel(Location location, String jwtToken){

        // Extract the username from the JWT token
        String username = jwtUtil.extractUsername(jwtToken);



    }
}
