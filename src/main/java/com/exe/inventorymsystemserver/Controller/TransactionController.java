package com.exe.inventorymsystemserver.Controller;

import com.exe.inventorymsystemserver.Exception.InsufficientStockException;
import com.exe.inventorymsystemserver.Exception.InvalidTransactionException;
import com.exe.inventorymsystemserver.Model.Transaction;
import com.exe.inventorymsystemserver.ResponseHandler.Response;
import com.exe.inventorymsystemserver.Service.ITransactionService;
import com.exe.inventorymsystemserver.Service.impl.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public Response createTransaction(
            @RequestBody Transaction transaction,
            @RequestHeader("Authorization") String jwtToken){
        try {
            Transaction createdTransaction = transactionService.createTransaction(transaction, jwtToken);
            return Response.success(createdTransaction);
        } catch (InsufficientStockException insufficientStockException){
            return Response.fail((insufficientStockException.getMessage()));
        }catch (InvalidTransactionException invalidTransactionException){
            return Response.fail((invalidTransactionException.getMessage()));
        }
    }
}
