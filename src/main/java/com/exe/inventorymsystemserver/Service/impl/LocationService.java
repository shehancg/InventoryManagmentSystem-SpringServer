package com.exe.inventorymsystemserver.Service.impl;

import com.exe.inventorymsystemserver.Dto.LocationDTO;
import com.exe.inventorymsystemserver.Exception.DuplicateLocationException;
import com.exe.inventorymsystemserver.Exception.InvalidLocationException;
import com.exe.inventorymsystemserver.Exception.InvalidLocationTypeException;
import com.exe.inventorymsystemserver.Model.Location;
import com.exe.inventorymsystemserver.Repository.ILocationRepository;
import com.exe.inventorymsystemserver.Service.ILocationService;
import com.exe.inventorymsystemserver.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
                updateLocation.setLocationName(location.getLocationName());


                // Save the Updated Location Back to the Database
                locationRepository.save(updateLocation);

                return updateLocation;
            } else {
                throw new InvalidLocationException("Location ID " + location.getLocationId() + " not found.");
            }
        }
        return locationRepository.save(location);
    }

    // Method to get All Locations
    public List<Location> getAllLocations(){
        return locationRepository.findAll();
    }

    // Methods to get All Location Names
    public List<String> getAllLocationNames(){
        List<Location> location = locationRepository.findAll();
        return location.stream()
                .map(Location::getLocationName)
                .collect(Collectors.toList());
    }

    public Map<String, List<LocationDTO>> getLocationListsByTypes(){
        List<Location> l1Locations = locationRepository.findByLocationType("L1");
        List<Location> l2Locations = locationRepository.findByLocationType("L2");
        List<Location> l3Locations = locationRepository.findByLocationType("L3");

        Map<String, List<LocationDTO>> locationLists = new HashMap<>();

        locationLists.put("L1", convertToLocationDTOList(l1Locations));
        locationLists.put("L2", convertToLocationDTOList(l2Locations));
        locationLists.put("L3", convertToLocationDTOList(l3Locations));

        return locationLists;
    }

    private List<LocationDTO> convertToLocationDTOList(List<Location> locations) {
        return locations.stream()
                .map(location -> new LocationDTO(location.getLocationId(), location.getLocationName()))
                .collect(Collectors.toList());
    }
}
