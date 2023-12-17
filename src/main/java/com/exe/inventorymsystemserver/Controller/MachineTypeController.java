package com.exe.inventorymsystemserver.Controller;

import com.exe.inventorymsystemserver.Model.MachineType;
import com.exe.inventorymsystemserver.Service.impl.MachineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/machine-types")
public class MachineTypeController {

    private final MachineTypeService machineTypeService;

    @Autowired
    public MachineTypeController(MachineTypeService machineTypeService) {
        this.machineTypeService = machineTypeService;
    }

    @PostMapping
    public ResponseEntity<MachineType> createMachineType(@RequestBody MachineType machineType, @RequestHeader("Authorization") String jwtToken) {
        MachineType createdMachineType = machineTypeService.createOrUpdateMachineType(machineType, jwtToken);
        return new ResponseEntity<>(createdMachineType, HttpStatus.CREATED);
    }

    @PutMapping("/{machineTypeId}")
    public ResponseEntity<MachineType> updateMachineType(@PathVariable Long machineTypeId, @RequestBody MachineType machineType, @RequestHeader("Authorization") String jwtToken) {
        MachineType updatedMachineType = machineTypeService.createOrUpdateMachineType(machineType, jwtToken);
        return new ResponseEntity<>(updatedMachineType, HttpStatus.OK);
    }

    // Other CRUD operations...

}
