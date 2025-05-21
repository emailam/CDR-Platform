package com.example.msbackend.exception;

// 404 not found
public class CDRNotFound extends RuntimeException {
    public CDRNotFound(String message) {
        super(message);
    }
}
