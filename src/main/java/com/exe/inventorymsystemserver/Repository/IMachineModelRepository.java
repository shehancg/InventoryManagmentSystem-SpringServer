package com.exe.inventorymsystemserver.Repository;

import com.exe.inventorymsystemserver.Model.MachineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMachineModelRepository extends JpaRepository<MachineModel, Long> {

    boolean existsByMachineModelNumber(String machineModelNumber);

    boolean existsByMachineModelNumberAndModelIdNot(String machineModelNumber, Long modelId);
}
