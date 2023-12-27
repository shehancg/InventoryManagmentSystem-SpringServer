package com.exe.inventorymsystemserver.Controller;

import com.exe.inventorymsystemserver.Dto.LocationDTO;
import com.exe.inventorymsystemserver.Exception.DuplicateLocationException;
import com.exe.inventorymsystemserver.Exception.InvalidLocationException;
import com.exe.inventorymsystemserver.Model.Location;
import com.exe.inventorymsystemserver.ResponseHandler.Response;
import com.exe.inventorymsystemserver.Service.ILocationService;
import com.exe.inventorymsystemserver.Service.impl.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final ILocationService locationService;

    @Autowired
    public LocationController(LocationService locationService){
        this.locationService = locationService;
    }

    @PostMapping("/create")
    public Response createLocation(
            @RequestBody Location location,
            @RequestHeader("Authorization") String jwtToken) {
        try {
            Location createdLocation = locationService.createOrUpdateLocationModel(location, jwtToken);
            return Response.success(createdLocation);
        } catch (DuplicateLocationException duplicateException) {
            return Response.fail(duplicateException.getMessage());
        } catch (InvalidLocationException invalidException) {
            return Response.fail(invalidException.getMessage());
        }
    }

    @PutMapping("/{locationId}")
    public Response updateLocation(@PathVariable Long locationId, @RequestBody Location location, @RequestHeader("Authorization") String jwtToken) {
        try {
            return Response.success(locationService.createOrUpdateLocationModel(location, jwtToken));
        } catch (DuplicateLocationException duplicateLocationException) {
            return Response.fail(duplicateLocationException.getMessage());
        } catch (InvalidLocationException invalidLocationException) {
            return Response.fail(invalidLocationException.getMessage());
        }
    }

    @GetMapping("/listsByTypes")
    public ResponseEntity<Map<String, List<LocationDTO>>> getLocationListsByTypes() {
        try {
            Map<String, List<LocationDTO>> locationLists = locationService.getLocationListsByTypes();
            return ResponseEntity.ok(locationLists);
        } catch (Exception e) {
            // Handle exceptions appropriately, you can customize this based on your needs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
