package com.lazday.crudjava;

public class SubmitResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SubmitResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
