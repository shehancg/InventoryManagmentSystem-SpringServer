package com.exe.inventorymsystemserver.Controller;

import com.exe.inventorymsystemserver.Exception.DuplicateMachineTypeException;
import com.exe.inventorymsystemserver.Exception.InvalidMachineTypeException;
import com.exe.inventorymsystemserver.Exception.ModelAttachToMachineTypeException;
import com.exe.inventorymsystemserver.Model.MachineModel;
import com.exe.inventorymsystemserver.Model.MachineType;
import com.exe.inventorymsystemserver.ResponseHandler.Response;
import com.exe.inventorymsystemserver.Service.IMachineTypeService;
import com.exe.inventorymsystemserver.Service.impl.MachineTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machinetypes")
@RequiredArgsConstructor
public class MachineTypeController {

    private final IMachineTypeService machineTypeService;

    @Autowired
    public MachineTypeController(MachineTypeService machineTypeService) {
        this.machineTypeService = machineTypeService;
    }

    @GetMapping
    public ResponseEntity<List<MachineType>> getAllMachineTypes() {
        List<MachineType> machineTypes = machineTypeService.getAllMachineTypes();
        return ResponseEntity.ok(machineTypes);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllMachineTypeNames() {
        List<String> machineTypeNames = machineTypeService.getAllMachineTypeNames();
        return ResponseEntity.ok(machineTypeNames);
    }

    // Get machine type by ID
    @GetMapping("/{machineTypeId}")
    public Response getMachineTypeById(@PathVariable Long machineTypeId) {
        try {
            MachineType machineType = machineTypeService.getMachineTypeById(machineTypeId);
            return Response.success(machineType);
        } catch (InvalidMachineTypeException invalidMachineTypeException) {
            return Response.fail(invalidMachineTypeException.getMessage());
        }
    }

    @DeleteMapping("/{machineTypeId}")
    public Response deleteMachineType(@PathVariable Long machineTypeId) {
        try {
            MachineType machineType = machineTypeService.deleteMachineType(machineTypeId);
            return Response.success(machineType);
        } catch (InvalidMachineTypeException invalidMachineTypeException) {
            return Response.fail(invalidMachineTypeException.getMessage());
        } catch (ModelAttachToMachineTypeException modelAttachToMachineTypeException) {
            // Catch more generic exceptions and handle them appropriately
            return Response.fail(modelAttachToMachineTypeException.getMessage());
        }
    }

    @PostMapping("/create")
    public Response createMachineType(@RequestBody MachineType machineType, @RequestHeader("Authorization") String jwtToken) {
        try {
            return Response.success(machineTypeService.createOrUpdateMachineType(machineType, jwtToken));
        } catch (DuplicateMachineTypeException duplicateMachineTypeException) {
            return Response.fail(duplicateMachineTypeException.getMessage());
        } catch (InvalidMachineTypeException invalidMachineTypeException) {
            return Response.fail(invalidMachineTypeException.getMessage());
        }
    }

    @PutMapping("/{machineTypeId}")
    public Response updateMachineType(@PathVariable Long machineTypeId, @RequestBody MachineType machineType, @RequestHeader("Authorization") String jwtToken) {
        try {
            return Response.success(machineTypeService.createOrUpdateMachineType(machineType, jwtToken));
        } catch (DuplicateMachineTypeException duplicateMachineTypeException) {
            return Response.fail(duplicateMachineTypeException.getMessage());
        } catch (InvalidMachineTypeException invalidMachineTypeException) {
            return Response.fail(invalidMachineTypeException.getMessage());
        }
    }
}
