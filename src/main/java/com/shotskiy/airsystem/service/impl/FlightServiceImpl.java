package com.shotskiy.airsystem.service.impl;

import com.shotskiy.airsystem.entity.AirCompany;
import com.shotskiy.airsystem.entity.Airplane;
import com.shotskiy.airsystem.entity.Flight;
import com.shotskiy.airsystem.model.FlightDateOnly;
import com.shotskiy.airsystem.model.FlightStatus;
import com.shotskiy.airsystem.repository.FlightRepository;
import com.shotskiy.airsystem.service.AirCompanyService;
import com.shotskiy.airsystem.service.AirplaneService;
import com.shotskiy.airsystem.service.FlightService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    //TODO
    @Override
    public Flight get(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found by id ="));
    }

    @Override
    public Flight save(Flight newFlight) {
        AirCompany airCompany = airCompanyService.get(newFlight.getAirCompany().getCompanyId());
        Airplane airplane = airplaneService.get(newFlight.getAirPlane().getAirplaneId());
        newFlight.setAirCompany(airCompany);
        newFlight.setAirPlane(airplane);
        newFlight.setFlightStatus(FlightStatus.PENDING);
        return flightRepository.save(newFlight);
    }

    @Override
    public void deleteById(Long id) {
        flightRepository.deleteById(id);
    }

    //TODO
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
                    if (!flight.getAirCompany().getCompanyId().equals(companyId)) {
                        flight.setAirCompany(airCompanyService.get(companyId));
                    }
                    Long airplaneId = updatedFlight.getAirPlane().getAirplaneId();
                    if (!flight.getAirPlane().getAirplaneId().equals(airplaneId)) {
                        flight.setAirPlane(airplaneService.get(airplaneId));
                    }
                    return flightRepository.save(flight);
                }).orElseThrow(() -> new RuntimeException("Flight not found by id ="));

    }

    @Override
    public List<Flight> getLongActiveFlights() {
        List<Flight> flights = flightRepository.findAllActive();
        flights = flights.stream()
                .filter((flight) -> isMoreThanDay(new Date(), flight.getStartedAt()))
                .collect(Collectors.toList());
        return flights;
    }

    public Flight setDelayedStatus(Long id, FlightDateOnly flightDateOnly) {
        Flight flight = get(id);
        flight.setFlightStatus(FlightStatus.DELAYED);
        Date started = flightDateOnly.getStarted();
        if (started == null) {
            started = new Date();
        }
        flight.setDelayStartedAt(started);
        return flightRepository.save(flight);
    }

    @Override
    public Flight setActiveStatus(Long id, FlightDateOnly flightDateOnly) {
        Flight flight = get(id);
        flight.setFlightStatus(FlightStatus.ACTIVE);
        Date started = flightDateOnly.getStarted();
        if (started == null) {
            started = new Date();
        }
        flight.setStartedAt(started);
        return flightRepository.save(flight);
    }

    @Override
    public Flight setCompletedStatus(Long id, FlightDateOnly flightDateOnly) {
        Flight flight = get(id);
        flight.setFlightStatus(FlightStatus.COMPLETED);
        Date ended = flightDateOnly.getStarted();
        if (ended == null) {
            ended = new Date();
        }
        flight.setEndedAt(ended);
        return flightRepository.save(flight);
    }

    @Override
    public List<Flight> getCompletedLateFlights() {
        List<Flight> flights = flightRepository.findAllCompleted();
        flights = flights.stream()
                .filter(flight -> isMoreThanEstimated(flight.getEstimatedFlightTime(), flight.getStartedAt(), flight.getEndedAt()))
                .collect(Collectors.toList());
        return flights;
    }

    //todo norm exep
    private boolean isMoreThanEstimated(String estimatedTime, Date startTime, Date endTime) {
        long flightDuration = endTime.getTime() - startTime.getTime();
        String[] time = estimatedTime.split(":");

        if (time.length != 3) {
            throw new RuntimeException();
        }

        long estimatedMills = 0;
        long hourInMills = 60L * 60L * 1000L;

        for (String s : time) {
            estimatedMills += Long.parseLong(s) * hourInMills;
            hourInMills = hourInMills / 60L;
        }

        return flightDuration > estimatedMills;
    }

    private boolean isMoreThanDay(Date startTime, Date endTime) {
        long milliseconds = startTime.getTime() - endTime.getTime();
        int hours = (int) (milliseconds / (60 * 60 * 1000));
        return hours >= 24;
    }

}
