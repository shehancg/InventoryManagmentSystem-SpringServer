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
    public Response createMachineModel(
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
}
