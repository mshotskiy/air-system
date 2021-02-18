package com.shotskiy.airsystem.service.impl;

import com.shotskiy.airsystem.entity.AirCompany;
import com.shotskiy.airsystem.entity.Airplane;
import com.shotskiy.airsystem.exception.AirplaneNotFoundException;
import com.shotskiy.airsystem.repository.AirplaneRepository;
import com.shotskiy.airsystem.service.AirCompanyService;
import com.shotskiy.airsystem.service.AirplaneService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirplaneServiceImpl implements AirplaneService {
    private final AirplaneRepository airplaneRepository;
    private final AirCompanyService airCompanyService;

    public AirplaneServiceImpl(AirplaneRepository airplaneRepository, AirCompanyService airCompanyService) {
        this.airplaneRepository = airplaneRepository;
        this.airCompanyService = airCompanyService;
    }

    @Override
    public Airplane get(Long id) {
        return airplaneRepository.findById(id)
                .orElseThrow(() -> new AirplaneNotFoundException(id));
    }

    @Override
    public List<Airplane> getAll() {
        return airplaneRepository.findAll();
    }

    @Override
    public Airplane save(Airplane newAirplane) {
        return airplaneRepository.save(newAirplane);
    }

    @Override
    public void deleteById(Long id) {
        airplaneRepository.deleteById(id);
    }

    @Override
    public Airplane update(Airplane updatedAirplane, Long id) {
        return airplaneRepository.findById(id)
                .map(airplane -> {
                    airplane.setName(updatedAirplane.getName());
                    airplane.setFactorySerialNumber(updatedAirplane.getFactorySerialNumber());
                    Long newCompanyId = updatedAirplane.getAirCompany().getCompanyId();
                    if (!airplane.getAirCompany().getCompanyId().equals(newCompanyId)) {
                        airplane.setAirCompany(airCompanyService.get(newCompanyId));
                    }
                    airplane.setNumberOfFlights(updatedAirplane.getNumberOfFlights());
                    airplane.setFlightDistance(updatedAirplane.getFlightDistance());
                    airplane.setFuelCapacity(updatedAirplane.getFuelCapacity());
                    airplane.setType(updatedAirplane.getType());
                    airplane.setCreatedAt(updatedAirplane.getCreatedAt());
                    return airplaneRepository.save(airplane);
                })
                .orElseThrow(() -> new AirplaneNotFoundException(id));
    }

    @Override
    public Airplane changeCompany(AirCompany airCompany, Long id) {
        Airplane airplane = airplaneRepository.findById(id)
                .orElseThrow(() -> new AirplaneNotFoundException(id));
        Long oldCompanyId = airplane.getAirCompany().getCompanyId();
        Long newCompanyId = airCompany.getCompanyId();

        if (oldCompanyId.equals(newCompanyId)) {
            return airplane;
        }
        airplane.setAirCompany(airCompanyService.get(newCompanyId));
        return airplaneRepository.save(airplane);
    }
}
