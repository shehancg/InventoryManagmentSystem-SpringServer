package com.exe.inventorymsystemserver.Service.impl;

import com.exe.inventorymsystemserver.Exception.*;
import com.exe.inventorymsystemserver.Model.Parts;
import com.exe.inventorymsystemserver.Model.Transaction;
import com.exe.inventorymsystemserver.Repository.IPartsRepository;
import com.exe.inventorymsystemserver.Repository.ITransactionRepository;
import com.exe.inventorymsystemserver.Service.ITransactionService;
import com.exe.inventorymsystemserver.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    private final ITransactionRepository transactionRepository;

    private final IPartsRepository partsRepository;

    private final JwtUtil jwtUtil;

    @Autowired
    private TransactionService(ITransactionRepository transactionRepository, IPartsRepository partsRepository, JwtUtil jwtUtil){
        this.partsRepository = partsRepository;
        this.transactionRepository =  transactionRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction, String jwtToken) {
        // Extract the username from the JWT token
        String username = jwtUtil.extractUsername(jwtToken);

        // Fetch the Parts entity using partId
        Parts part = getPartById(transaction.getPartId());

        // Update the quantity in the Parts table based on the transaction type
        updatePartsQuantity(transaction, part);

        // Save the
        transaction.setName(part.getPartNumber());
        transaction.setPart(part);
        transaction.setCreatedBy(username);
        transaction.setCreatedDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    private void updatePartsQuantity(Transaction transaction, Parts part) {

        // Validate transaction type and quantity
        if (transaction.getTransactionType() == null) {
            throw new InvalidTransactionException("Transaction type is null for part: " + part.getPartId());
        }

        int transactionQuantity = transaction.getQuantity();

        if (transactionQuantity <= 0) {
            throw new InvalidTransactionException("Invalid transaction quantity for part: " + part.getPartId());
        }

        // Check if transaction quantity is greater than available quantity
        if (("StockOut".equalsIgnoreCase(transaction.getTransactionType())) && (transactionQuantity > part.getQuantity())) {
            throw new InsufficientStockException("Transaction quantity exceeds available quantity for part: " + part.getPartId());
        }

        // Update the quantity in the Parts table
        int quantityChange = ("StockIn".equalsIgnoreCase(transaction.getTransactionType())) ? transaction.getQuantity() : -transaction.getQuantity();

        // Update the quantity in the Parts table
        int updatedQuantity = part.getQuantity() + quantityChange;
        part.setQuantity(updatedQuantity);

        // Calculate Price and Total
        calculatePriceAndTotal(transaction,part);

        // Save the updated part
        partsRepository.save(part);
    }

    private void calculatePriceAndTotal(Transaction transaction, Parts parts) {

        // Calculate Price and Total
        double price = parts.getPrice();
        int quantity = transaction.getQuantity();
        double total = price * quantity;

        // Set the calculated values for transaction
        transaction.setPrice(price);
        transaction.setTotal(total);
    }

    private Parts getPartById(Long partId) {
        return partsRepository.findById(partId)
                .orElseThrow(() -> new InvalidPartException("Part with ID " + partId + " not found."));
    }

    // Method to Get ALL Transactions
    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }
}
