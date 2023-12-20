package com.exe.inventorymsystemserver.Exception;

public class DuplicateMachineTypeException extends RuntimeException{

    public DuplicateMachineTypeException(String message){
        super(message);
    }

    public DuplicateMachineTypeException(String message,Throwable cause){
        super(message, cause);
    }
}
