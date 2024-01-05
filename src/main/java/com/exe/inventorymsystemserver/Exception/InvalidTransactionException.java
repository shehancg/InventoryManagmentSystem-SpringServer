package com.exe.inventorymsystemserver.Exception;

public class InvalidTransactionException extends RuntimeException{

    public InvalidTransactionException(String message){
        super(message);
    }

    public InvalidTransactionException(String message,Throwable cause){
        super(message, cause);
    }
}
