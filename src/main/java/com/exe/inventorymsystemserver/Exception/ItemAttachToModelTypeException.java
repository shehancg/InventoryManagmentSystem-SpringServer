package com.exe.inventorymsystemserver.Exception;

public class ItemAttachToModelTypeException extends RuntimeException{

    public ItemAttachToModelTypeException(String message){
        super(message);
    }

    public ItemAttachToModelTypeException(String message,Throwable cause){
        super(message, cause);
    }
}
