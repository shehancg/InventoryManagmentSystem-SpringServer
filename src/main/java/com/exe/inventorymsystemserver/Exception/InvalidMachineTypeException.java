package com.exe.inventorymsystemserver.Exception;

public class InvalidMachineTypeException extends RuntimeException{

    public InvalidMachineTypeException(String message){
        super(message);
    }

    public InvalidMachineTypeException(String message,Throwable cause){
        super(message, cause);
    }
}
