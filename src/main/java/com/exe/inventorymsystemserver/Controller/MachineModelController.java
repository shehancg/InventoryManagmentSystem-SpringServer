package com.exe.inventorymsystemserver.Controller;

import com.exe.inventorymsystemserver.Exception.DuplicateMachineModelException;
import com.exe.inventorymsystemserver.Exception.InvalidMachineModelException;
import com.exe.inventorymsystemserver.Exception.ItemAttachToModelTypeException;
import com.exe.inventorymsystemserver.Model.MachineModel;
import com.exe.inventorymsystemserver.ResponseHandler.Response;
import com.exe.inventorymsystemserver.Service.IMachineModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/machinemodels")
@RequiredArgsConstructor
public class MachineModelController {

    private final IMachineModelService machineModelService;

    @PostMapping("/create")
    public Response createMachineModel(
            @RequestParam("pdfFile") MultipartFile pdfFile,
            @ModelAttribute MachineModel machineModel,
            @RequestHeader("Authorization") String jwtToken) {
        try {
            machineModel.setPdfFile(pdfFile);
            MachineModel createdMachineModel = machineModelService.createOrUpdateMachineModel(machineModel, jwtToken);
            return Response.success(createdMachineModel);
        } catch (DuplicateMachineModelException duplicateException) {
            return Response.fail(duplicateException.getMessage());
        } catch (InvalidMachineModelException invalidException) {
            return Response.fail(invalidException.getMessage());
        }
    }

    @PutMapping("/{modelId}")
    public Response updateMachineModel(
            @RequestParam("pdfFile") MultipartFile pdfFile,
            @ModelAttribute MachineModel machineModel,
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable Long modelId) {
        try {
            machineModel.setPdfFile(pdfFile);
            // Pass the modelId to the service method
            MachineModel updatedMachineModel = machineModelService.createOrUpdateMachineModel(machineModel, jwtToken);
            return Response.success(updatedMachineModel);
        } catch (DuplicateMachineModelException duplicateException) {
            return Response.fail(duplicateException.getMessage());
        } catch (InvalidMachineModelException invalidException) {
            return Response.fail(invalidException.getMessage());
        }
    }

    // Get all Machine Models
    @GetMapping
    public ResponseEntity<List<MachineModel>> getAllMachineTypes() {
        List<MachineModel> machineModels = machineModelService.getAllMachineModels();
        return ResponseEntity.ok(machineModels);
    }

    // Get Names of all machine models
    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllMachineModelNames() {
        List<String> machineModelNames = machineModelService.getAllMachineModelNames();
        return ResponseEntity.ok(machineModelNames);
    }

    @DeleteMapping("/{modelId}")
    public Response deleteMachineModel(@PathVariable Long modelId) {
        try {
            MachineModel machineModel = machineModelService.deleteMachineModel(modelId);
            return Response.success(machineModel);
        } catch (InvalidMachineModelException invalidMachineTypeException) {
            return Response.fail(invalidMachineTypeException.getMessage());
        } catch (ItemAttachToModelTypeException modelAttachToMachineTypeException) {
            // Catch more generic exceptions and handle them appropriately
            return Response.fail(modelAttachToMachineTypeException.getMessage());
        }
    }
}
