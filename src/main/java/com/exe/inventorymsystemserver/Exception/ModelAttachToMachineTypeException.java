package com.exe.inventorymsystemserver.Exception;

public class ModelAttachToMachineTypeException extends RuntimeException{

    public ModelAttachToMachineTypeException(String message){
        super(message);
    }

    public ModelAttachToMachineTypeException(String message,Throwable cause){
        super(message, cause);
    }
}
