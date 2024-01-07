package com.exe.inventorymsystemserver.Service;

import com.exe.inventorymsystemserver.Model.Transaction;

import java.util.List;

public interface ITransactionService {
    Transaction createTransaction(Transaction transaction, String jwtToken);

    List<Transaction> getAllTransactions();
}
