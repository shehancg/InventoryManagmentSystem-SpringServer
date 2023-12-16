package com.exe.inventorymsystemserver.Repository;

import com.exe.inventorymsystemserver.Model.UserLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserLogsRepository extends JpaRepository<UserLogs, Long> {
}
