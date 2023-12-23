package com.exe.inventorymsystemserver.Repository;

import com.exe.inventorymsystemserver.Model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocationRepository extends JpaRepository<Location, Long> {

    boolean existsByLocationName(String locationName);
}
