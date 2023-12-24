package com.exe.inventorymsystemserver.Controller;

import com.exe.inventorymsystemserver.Exception.DuplicateLocationException;
import com.exe.inventorymsystemserver.Exception.InvalidLocationException;
import com.exe.inventorymsystemserver.Model.Location;
import com.exe.inventorymsystemserver.ResponseHandler.Response;
import com.exe.inventorymsystemserver.Service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private ILocationService locationService;

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
}
