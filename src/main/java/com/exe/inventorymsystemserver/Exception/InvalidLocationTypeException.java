package com.exe.inventorymsystemserver.Exception;

public class InvalidLocationTypeException extends RuntimeException{

    public InvalidLocationTypeException(String message){
        super(message);
    }

    public InvalidLocationTypeException(String message,Throwable cause){
        super(message, cause);
    }
}