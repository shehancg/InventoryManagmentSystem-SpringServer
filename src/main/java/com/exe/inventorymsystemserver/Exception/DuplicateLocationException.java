package com.exe.inventorymsystemserver.Exception;

public class DuplicateLocationException extends RuntimeException{

    public DuplicateLocationException(String message){
        super(message);
    }

    public DuplicateLocationException(String message,Throwable cause){
        super(message, cause);
    }
}
