package com.exe.inventorymsystemserver.Exception;

public class UnknownException extends RuntimeException{

    public UnknownException(String message){
        super(message);
    }

    public UnknownException(String message,Throwable cause){
        super(message, cause);
    }
}

