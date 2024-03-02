package com.exe.inventorymsystemserver.Exception;

public class ItemAttachToLocationException extends RuntimeException{

    public ItemAttachToLocationException(String message){
        super(message);
    }

    public ItemAttachToLocationException(String message,Throwable cause){
        super(message, cause);
    }
}
