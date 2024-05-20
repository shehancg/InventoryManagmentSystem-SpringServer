package com.exe.inventorymsystemserver.ResponseHandler;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response {

    private boolean action;
    private Object data;
    private Status status;
    private String message;

    private enum Status {
        SUCCESS, FAIL, SYSTEM_DOWN, INVALID_LOGIN
    }

    private Response(Status status, boolean action, Object data, String message) {
        this.status = status;
        this.action = action;
        this.data = data;
        this.message = message;
    }

    public Response() {

    }

    public static Response success(Object data) {
        return new Response(Status.SUCCESS, true, data, "SUCCESS");
    }

    public static Response fail(String message) {
        return new Response(Status.FAIL, false, null, message);
    }

    public static Response systemDown(String message) {
        return new Response(Status.SYSTEM_DOWN, false, null, message);
    }

    public static Response invalidLogin(String message) {
        return new Response(Status.INVALID_LOGIN, false, null, message);
    }
}
