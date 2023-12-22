package com.exe.inventorymsystemserver.Controller;

import com.exe.inventorymsystemserver.Exception.DuplicateMachineModelException;
import com.exe.inventorymsystemserver.Exception.InvalidMachineModelException;
import com.exe.inventorymsystemserver.Model.MachineModel;
import com.exe.inventorymsystemserver.ResponseHandler.Response;
import com.exe.inventorymsystemserver.Service.impl.MachineModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/machinemodels")
public class MachineModelController {

    @Autowired
    private MachineModelService machineModelService;

    /*@PostMapping("/create")
    public ResponseEntity<MachineModel> createMachineModel(
            @RequestParam("pdfFile") MultipartFile pdfFile,
            @ModelAttribute MachineModel machineModel,
            @RequestHeader("Authorization") String jwtToken) {
        machineModel.setPdfFile(pdfFile);
        MachineModel createdMachineModel = machineModelService.createOrUpdateMachineModel(machineModel, jwtToken);
        return new ResponseEntity<>(createdMachineModel, HttpStatus.CREATED);
    }*/

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


}
