package com.exe.inventorymsystemserver.Exception;

public class InvalidLocationException extends RuntimeException{

    public InvalidLocationException(String message){
        super(message);
    }

    public InvalidLocationException(String message,Throwable cause){
        super(message, cause);
    }
}
