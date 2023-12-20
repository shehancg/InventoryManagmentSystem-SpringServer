package com.exe.inventorymsystemserver.Controller;

import com.exe.inventorymsystemserver.Exception.DuplicateMachineTypeException;
import com.exe.inventorymsystemserver.Exception.InvalidMachineTypeException;
import com.exe.inventorymsystemserver.Model.MachineType;
import com.exe.inventorymsystemserver.ResponseHandler.Response;
import com.exe.inventorymsystemserver.Service.impl.MachineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machinetypes")
public class MachineTypeController {

    private final MachineTypeService machineTypeService;

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

    @DeleteMapping("/{machineTypeId}")
    public ResponseEntity<String> deleteMachineType(@PathVariable Long machineTypeId) {
        try {
            machineTypeService.deleteMachineType(machineTypeId);
            return new ResponseEntity<>("Machine Type deleted successfully", HttpStatus.OK);
        } catch (InvalidMachineTypeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

/*  @PostMapping
    public ResponseEntity<MachineType> createMachineType(@RequestBody MachineType machineType, @RequestHeader("Authorization") String jwtToken) {
        MachineType createdMachineType = machineTypeService.createOrUpdateMachineType(machineType, jwtToken);
        return new ResponseEntity<>(createdMachineType, HttpStatus.CREATED);
    }

    @PutMapping("/{machineTypeId}")
    public ResponseEntity<MachineType> updateMachineType(@PathVariable Long machineTypeId, @RequestBody MachineType machineType, @RequestHeader("Authorization") String jwtToken) {
        MachineType updatedMachineType = machineTypeService.createOrUpdateMachineType(machineType, jwtToken);
        return new ResponseEntity<>(updatedMachineType, HttpStatus.OK);
    }*/

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
