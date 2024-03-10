package com.example.deliveryservice.exception;

public class CourierNotAvailableException extends RuntimeException {
    public CourierNotAvailableException(String message) {
        super(message);
    }
}
