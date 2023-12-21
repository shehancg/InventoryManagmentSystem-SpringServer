package com.exe.inventorymsystemserver.Exception;

public class DuplicateMachineModelException extends RuntimeException{

    public DuplicateMachineModelException(String message){
        super(message);
    }

    public DuplicateMachineModelException(String message,Throwable cause){
        super(message, cause);
    }
}
