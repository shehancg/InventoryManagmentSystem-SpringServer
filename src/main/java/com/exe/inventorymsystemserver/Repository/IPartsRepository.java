package com.exe.inventorymsystemserver.Repository;

import com.exe.inventorymsystemserver.Model.Parts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPartsRepository extends JpaRepository<Parts, Long> {

    @Modifying
    @Query("UPDATE Parts p SET p.status = false WHERE p.partId = :partId")
    void deletePart(@Param("partId") Long partId);

    boolean existsByPartNumber(String partNumber);

    boolean existsByPartNumberAndPartIdNot(String partNumber, Long partId);

    List<Parts> findByStatus(boolean status);

    List<Parts> findByQuantityLessThanAndLimitQuantityIsNotNull(int limitQuantity);

    Optional<Parts> findByPartIdAndStatus(Long partId, boolean status);

    Parts findByPartNumberAndStatus(String partNumber, boolean status);

    List<Parts> findByLocation1OrLocation2OrLocation3(String locationId, String locationId2, String locationId3);

    boolean existsByLocation1OrLocation2OrLocation3(String locationId, String locationId2, String locationId3);
}
