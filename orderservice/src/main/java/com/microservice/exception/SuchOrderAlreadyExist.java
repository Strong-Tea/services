package com.microservice.exception;

public class SuchOrderAlreadyExist extends RuntimeException {
    public SuchOrderAlreadyExist(String message) {
        super(message);
    }
}
