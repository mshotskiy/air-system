package com.shotskiy.airsystem.service;

import com.shotskiy.airsystem.entity.Flight;

import java.util.List;

public interface FlightService extends AbstractEntityService<Flight>{
    List<Flight> getLongActiveFlights();
    
    
}
