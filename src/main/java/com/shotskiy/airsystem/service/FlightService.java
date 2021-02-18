package com.shotskiy.airsystem.service;

import com.shotskiy.airsystem.entity.Flight;
import com.shotskiy.airsystem.model.FlightDateOnly;

import java.util.Date;
import java.util.List;

public interface FlightService extends AbstractEntityService<Flight>{
    List<Flight> getLongActiveFlights();
    Flight setDelayedStatus(Long id, FlightDateOnly flightDateOnly);
    Flight setActiveStatus(Long id, FlightDateOnly flightDateOnly);
    Flight setCompletedStatus(Long id, FlightDateOnly flightDateOnly);

    List<Flight> getCompletedLateFlights();
}
