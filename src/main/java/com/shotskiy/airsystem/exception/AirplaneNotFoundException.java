package com.shotskiy.airsystem.exception;

public class AirplaneNotFoundException extends RuntimeException{
    public AirplaneNotFoundException(Long id) {
        super("Could not find airplane" + id);
    }
}
