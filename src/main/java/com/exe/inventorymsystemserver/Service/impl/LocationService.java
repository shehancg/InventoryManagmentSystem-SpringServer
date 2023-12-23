package com.exe.inventorymsystemserver.Service.impl;

import com.exe.inventorymsystemserver.Exception.DuplicateLocationException;
import com.exe.inventorymsystemserver.Exception.InvalidLocationException;
import com.exe.inventorymsystemserver.Exception.InvalidLocationTypeException;
import com.exe.inventorymsystemserver.Model.Location;
import com.exe.inventorymsystemserver.Repository.ILocationRepository;
import com.exe.inventorymsystemserver.Service.ILocationService;
import com.exe.inventorymsystemserver.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LocationService implements ILocationService {

    @Autowired
    private ILocationRepository locationRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public Location createOrUpdateLocationModel(Location location, String jwtToken)  {

        // Extract the username from the JWT token
        String username = jwtUtil.extractUsername(jwtToken);

        // Check if Location has a non-null modelId if not then UPDATE
        if (location.getLocationId() == null){

            // Creating a new Location
            if (location.getLocationName() == null){
                // Throw exception if location is null
                throw new InvalidLocationException("Location Cannot Be Null");
            }

            // Check if Location Type is Entered
            if (location.getLocationType() == null){
                throw new InvalidLocationTypeException("Location Type Cannot Be Empty");
            }

            // Check if Duplicate Location Exists
            if (locationRepository.existsByLocationName(location.getLocationName())){
                throw new DuplicateLocationException("Same Location Name Already Exists");
            }

            // Creating New Location
            location.setCreatedBy(username);
            location.setCreatedDate(LocalDateTime.now());
            location.setStatus(true);

        } else {
            Optional<Location> existingLocation = locationRepository.findById(location.getLocationId());

            // Updating an Existing Location
            if (existingLocation.isPresent()) {

                if (location.getLocationName() == null) {
                    // Throw exception if location is null
                    throw new InvalidLocationException("Location Cannot Be Null");
                }

                // Check if Duplicate Location Exists
                if (locationRepository.existsByLocationName(location.getLocationName())) {
                    throw new DuplicateLocationException("Same Location Name Already Exists");
                }

                // Update only the necessary Fields
                Location updateLocation = existingLocation.get();
                updateLocation.setModifyBy(username);
                updateLocation.setModifyDate(LocalDateTime.now());


                // Save the Updated Location Back to the Database
                locationRepository.save(updateLocation);

                return updateLocation;
            } else {
                throw new InvalidLocationException("Location ID " + location.getLocationId() + " not found.");
            }
        }
        return locationRepository.save(location);
    }
}
