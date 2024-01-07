package com.exe.inventorymsystemserver.Service;

import com.exe.inventorymsystemserver.Model.Parts;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IPartsService {
    Parts createOrUpdatePart(Parts parts, String jwtToken, List<Long> machineModelIds, MultipartFile imageFile11, MultipartFile imageFile2);

    @Transactional
    void deleteItem(Long partId);

    List<Parts> getAllPartModels();

    Optional<Parts> getPartById(Long partId);

    List<Parts> getAllPartsBelowLimitQuantity();
}
