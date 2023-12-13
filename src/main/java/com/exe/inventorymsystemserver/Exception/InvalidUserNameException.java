package com.exe.inventorymsystemserver.Exception;

public class InvalidUserNameException extends RuntimeException{

    public InvalidUserNameException(String message){
        super(message);
    }
    public InvalidUserNameException(String message,Throwable cause){
        super(message, cause);
    }

}
