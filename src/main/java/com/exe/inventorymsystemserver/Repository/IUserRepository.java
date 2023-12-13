package com.exe.inventorymsystemserver.Repository;

import com.exe.inventorymsystemserver.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User ,Long> {

    @Modifying
    @Query("UPDATE User u SET u.isActive = false WHERE u.userId = ?1")
    void DeleteUser(Long userId);

    public User findByUserName(String username);

    // Add special Queries if needed
}
