package com.shotskiy.airsystem.service.impl;

import com.shotskiy.airsystem.entity.Airplane;
import com.shotskiy.airsystem.entity.Flight;
import com.shotskiy.airsystem.entity.AirCompany;
import com.shotskiy.airsystem.exception.ResourceNotFoundException;
import com.shotskiy.airsystem.repository.FlightRepository;
import com.shotskiy.airsystem.service.AirCompanyService;
import com.shotskiy.airsystem.service.AirplaneService;
import com.shotskiy.airsystem.service.FlightService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final AirCompanyService airCompanyService;
    private final AirplaneService airplaneService;

    public FlightServiceImpl(FlightRepository flightRepository, AirCompanyService airCompanyService, AirplaneService airplaneService) {
        this.flightRepository = flightRepository;
        this.airCompanyService = airCompanyService;
        this.airplaneService = airplaneService;
    }

    @Override
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }

    @Override
    public Flight get(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found by id =", id));
    }

    @Override
    public Flight save(Flight newFlight) {
        AirCompany airCompany = airCompanyService.get(newFlight.getAirCompany().getCompanyId());
        Airplane airplane = airplaneService.get(newFlight.getAirPlane().getAirplaneId());
        newFlight.setAirCompany(airCompany);
        newFlight.setAirPlane(airplane);
        return flightRepository.save(newFlight);
    }

    @Override
    public void deleteById(Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    public Flight update(Flight updatedFlight, Long id) {
        return flightRepository.findById(id)
                .map(flight -> {
                    flight.setFlightStatus(updatedFlight.getFlightStatus());
                    flight.setDepartureCountry(updatedFlight.getDepartureCountry());
                    flight.setDestinationCountry(updatedFlight.getDestinationCountry());
                    flight.setDistance(updatedFlight.getDistance());
                    flight.setEstimatedFlightTime(updatedFlight.getEstimatedFlightTime());
                    flight.setEndedAt(updatedFlight.getEndedAt());
                    flight.setDelayStartedAt(updatedFlight.getDelayStartedAt());
                    flight.setCreatedAt(updatedFlight.getCreatedAt());
                    Long companyId = updatedFlight.getAirCompany().getCompanyId();
                    if (!flight.getAirCompany().getCompanyId().equals(companyId)){
                        flight.setAirCompany(airCompanyService.get(companyId));
                    }
                    Long airplaneId = updatedFlight.getAirPlane().getAirplaneId();
                    if (!flight.getAirPlane().getAirplaneId().equals(airplaneId)){
                        flight.setAirPlane(airplaneService.get(airplaneId));
                    }
                    return flightRepository.save(flight);
                }).orElseThrow(() -> new ResourceNotFoundException("Flight not found by id =", id));

    }

    @Override
    public List<Flight> getLongActiveFlights() {
        List<Flight> flights = flightRepository.findAllActive();
        flights = flights.stream()
                .filter((flight) ->  getHoursBetween(new Date(), flight.getStartedAt()))
                .collect(Collectors.toList());
        return flights;
    }

    private boolean getHoursBetween(Date startTime, Date endTime) {
        long milliseconds = startTime.getTime() - endTime.getTime();
        int hours = (int) (milliseconds / (60 * 60 * 1000));
        return hours >= 24;
    }
}
