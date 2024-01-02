package com.exe.inventorymsystemserver.Controller;

import com.exe.inventorymsystemserver.Exception.DuplicatePartNumberException;
import com.exe.inventorymsystemserver.Exception.InvalidPartException;
import com.exe.inventorymsystemserver.Model.Parts;
import com.exe.inventorymsystemserver.ResponseHandler.Response;
import com.exe.inventorymsystemserver.Service.IPartsService;
import com.exe.inventorymsystemserver.Service.impl.PartsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parts")
@RequiredArgsConstructor
public class PartsController {

    private final IPartsService partsService;

    @Autowired
    public PartsController(PartsService partsService){
        this.partsService = partsService;
    }

    /*@PostMapping("/createOrUpdate")
    public ResponseEntity<?> createOrUpdatePart(@ModelAttribute Parts parts,
                                                @RequestParam("jwtToken") String jwtToken,
                                                @RequestParam("machineModelIds") List<Long> machineModelIds,
                                                @RequestParam(value = "imageFile11", required = false) MultipartFile imageFile11,
                                                @RequestParam(value = "imageFile2", required = false) MultipartFile imageFile2) {
        try {
            Parts savedPart = partsService.createOrUpdatePart(parts, jwtToken, machineModelIds, imageFile11, imageFile2);
            return new ResponseEntity<>(savedPart, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    @PostMapping("/create")
    public Response createPart(
            @RequestParam("imageFile1") MultipartFile imageFile1,
            @RequestParam("imageFile2") MultipartFile imageFile2,
            @RequestParam("machineModelIds") List<Long> machineModelIds,
            @ModelAttribute Parts parts,
            @RequestHeader("Authorization") String jwtToken) {
        try {
            parts.setImageFile1(imageFile1);
            parts.setImageFile2(imageFile2);
            Parts createdPart = partsService.createOrUpdatePart(parts, jwtToken, machineModelIds, imageFile1, imageFile2);
            return Response.success(createdPart);
        } catch (InvalidPartException invalidPartException) {
            return Response.fail(invalidPartException.getMessage());
        } catch (DuplicatePartNumberException duplicatePartNumberException) {
            return Response.fail(duplicatePartNumberException.getMessage());
        }
    }

    @PutMapping("/{partId}")
    public Response updatePart(
            @RequestParam("imageFile1") MultipartFile imageFile1,
            @RequestParam("imageFile2") MultipartFile imageFile2,
            @RequestParam("machineModelIds") List<Long> machineModelIds,
            @ModelAttribute Parts parts,
            @RequestHeader("Authorization") String jwtToken) {
        try {
            parts.setImageFile1(imageFile1);
            parts.setImageFile2(imageFile2);
            Parts createdPart = partsService.createOrUpdatePart(parts, jwtToken, machineModelIds, imageFile1, imageFile2);
            return Response.success(createdPart);
        } catch (InvalidPartException invalidPartException) {
            return Response.fail(invalidPartException.getMessage());
        } catch (DuplicatePartNumberException duplicatePartNumberException) {
            return Response.fail(duplicatePartNumberException.getMessage());
        }
    }

    /*@PutMapping("delete/{partId}")
    public ResponseEntity<String> updatePart(
            @PathVariable Long partId) {
        try {
            partsService.deleteItem(partId);
            return new ResponseEntity<>("Part updated successfully", HttpStatus.OK);
        } catch (InvalidPartException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating the part", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @PutMapping("delete/{partId}")
    public Response updatePart(
            @PathVariable Long partId) {
        try {
            partsService.deleteItem(partId);
            return Response.success(partId);
        } catch (InvalidPartException e) {
            return Response.fail(e.getMessage());
            //return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return Response.fail(e.getMessage());
            //return new ResponseEntity<>("An error occurred while updating the part", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get All Parts
    @GetMapping
    public ResponseEntity<List<Parts>> getAllParts() {
        List<Parts> allParts = partsService.getAllPartModels();
        return new ResponseEntity<>(allParts, HttpStatus.OK);
    }

    // Get Part By Id
    @GetMapping("/{partId}")
    public ResponseEntity<Parts> getPartById(@PathVariable Long partId) {
        Optional<Parts> partOptional = partsService.getPartById(partId);

        return partOptional.map(part -> new ResponseEntity<>(part, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
