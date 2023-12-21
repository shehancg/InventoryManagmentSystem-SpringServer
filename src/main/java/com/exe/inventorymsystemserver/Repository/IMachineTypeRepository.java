package com.exe.inventorymsystemserver.Repository;

import com.exe.inventorymsystemserver.Model.MachineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMachineTypeRepository extends JpaRepository<MachineType, Long> {
    boolean existsByMachineTypeName(String machineTypeName);
}
