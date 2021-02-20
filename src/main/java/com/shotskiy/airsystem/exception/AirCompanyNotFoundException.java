package com.shotskiy.airsystem.exception;

public class AirCompanyNotFoundException extends RuntimeException {

    public AirCompanyNotFoundException(Long id) {
        super("Could not find air company " + id);
    }

    public AirCompanyNotFoundException(String name) {
        super("Could not find air company " + name);
    }
}
