package com.exe.inventorymsystemserver.Service;

import com.exe.inventorymsystemserver.Model.Transaction;

public interface ITransactionService {
    Transaction createTransaction(Transaction transaction, String jwtToken);
}
