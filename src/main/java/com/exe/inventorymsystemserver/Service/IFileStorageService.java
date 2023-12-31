package com.exe.inventorymsystemserver.Service;

import org.springframework.core.io.Resource;

public interface IFileStorageService {
    Resource downloadFile(String fileName);
}
