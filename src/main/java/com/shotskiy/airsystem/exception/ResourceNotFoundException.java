package com.shotskiy.airsystem.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message, Long id) {
        super(message + id);
    }
}
