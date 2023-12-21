package com.exe.inventorymsystemserver.Exception;

public class InvalidMachineModelException extends RuntimeException{

    public InvalidMachineModelException(String message){
        super(message);
    }

    public InvalidMachineModelException(String message,Throwable cause){
        super(message, cause);
    }
}
