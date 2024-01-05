package com.exe.inventorymsystemserver.Repository;

import com.exe.inventorymsystemserver.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction,Long> {


}
