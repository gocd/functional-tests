package com.thoughtworks.cruise.api.response;

public class SimpleMessage {
    private final String message;


    public SimpleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
