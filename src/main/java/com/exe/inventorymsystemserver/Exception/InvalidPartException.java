package com.exe.inventorymsystemserver.Exception;

public class InvalidPartException extends RuntimeException {

    public InvalidPartException(String message) {
        super(message);
    }

    public InvalidPartException(String message, Throwable cause) {
        super(message, cause);
    }
}