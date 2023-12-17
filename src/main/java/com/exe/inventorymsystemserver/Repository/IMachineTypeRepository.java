package com.exe.inventorymsystemserver.Repository;

import com.exe.inventorymsystemserver.Model.MachineType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMachineTypeRepository extends JpaRepository<MachineType, Long> {


}
