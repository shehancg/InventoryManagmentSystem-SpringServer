package com.exe.inventorymsystemserver.Exception;

public class DuplicatePartNumberException extends RuntimeException{

    public DuplicatePartNumberException(String message){
        super(message);
    }

    public DuplicatePartNumberException(String message,Throwable cause){
        super(message, cause);
    }
}