package com.exe.inventorymsystemserver.Repository;

import com.exe.inventorymsystemserver.Model.Parts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPartsRepository extends JpaRepository<Parts, Long> {

    boolean existsByPartNumber(String partNumber);
}
