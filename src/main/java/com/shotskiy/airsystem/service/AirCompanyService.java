package com.shotskiy.airsystem.service;

import com.shotskiy.airsystem.entity.AirCompany;
import com.shotskiy.airsystem.entity.Flight;
import com.shotskiy.airsystem.model.FlightStatus;

import java.util.List;

public interface AirCompanyService extends AbstractEntityService<AirCompany> {
    List<Flight> findFlightsByStatus(String name, FlightStatus status);
}
