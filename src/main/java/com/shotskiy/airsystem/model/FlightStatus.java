package com.shotskiy.airsystem.model;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum FlightStatus {
    ACTIVE, COMPLETED, DELAYED, PENDING;

    public String getDbValue(){
        String value = name();
        value = value.toLowerCase();
        value = value.substring(0, 1).toUpperCase() + value.substring(1);
        return value;
    }



    @Converter
    public static class PersistJPAConverter implements AttributeConverter<FlightStatus, String> {

        @Override
        public String convertToDatabaseColumn(FlightStatus flightStatus) {
            return flightStatus.getDbValue();
        }

        @Override
        public FlightStatus convertToEntityAttribute(String s) {
            return FlightStatus.valueOf(s.toUpperCase());
        }
    }
}
