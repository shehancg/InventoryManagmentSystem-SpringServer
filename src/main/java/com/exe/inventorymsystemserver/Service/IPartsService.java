package com.exe.inventorymsystemserver.Service;

import com.exe.inventorymsystemserver.Model.Parts;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPartsService {
    Parts createOrUpdatePart(Parts parts, String jwtToken, List<Long> machineModelIds, MultipartFile imageFile11, MultipartFile imageFile2);
}
