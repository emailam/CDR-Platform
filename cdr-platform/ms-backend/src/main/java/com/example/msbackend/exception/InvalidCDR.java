package com.example.msbackend.exception;

public class InvalidCDR extends RuntimeException {
    public InvalidCDR(String message) {
        super(message);
    }
}
