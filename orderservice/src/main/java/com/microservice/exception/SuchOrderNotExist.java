package com.microservice.exception;

public class SuchOrderNotExist extends RuntimeException {
    public SuchOrderNotExist(String message) {
        super(message);
    }
}
