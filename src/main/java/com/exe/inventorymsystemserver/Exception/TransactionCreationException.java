package com.exe.inventorymsystemserver.Exception;

public class TransactionCreationException extends RuntimeException{

    public TransactionCreationException(String message){
        super(message);
    }

    public TransactionCreationException(String message,Throwable cause){
        super(message, cause);
    }
}

