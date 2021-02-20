package com.shotskiy.airsystem.exception;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(Long id) {
        super("Can not find flight " + id);
    }
}
