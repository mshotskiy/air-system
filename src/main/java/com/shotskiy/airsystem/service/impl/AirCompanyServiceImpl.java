package com.shotskiy.airsystem.service.impl;

import com.shotskiy.airsystem.entity.AirCompany;
import com.shotskiy.airsystem.entity.Flight;
import com.shotskiy.airsystem.exception.AirCompanyNotFoundException;
import com.shotskiy.airsystem.model.FlightStatus;
import com.shotskiy.airsystem.repository.AirCompanyRepository;
import com.shotskiy.airsystem.service.AirCompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirCompanyServiceImpl implements AirCompanyService {
    private final AirCompanyRepository airCompanyRepository;

    public AirCompanyServiceImpl(AirCompanyRepository airCompanyRepository) {
        this.airCompanyRepository = airCompanyRepository;
    }

    public AirCompany get(Long id) {
        return airCompanyRepository.findById(id).orElseThrow(() -> new AirCompanyNotFoundException(id));
    }

    @Override
    public List<AirCompany> getAll() {
        return airCompanyRepository.findAll();
    }

    @Override
    public AirCompany save(AirCompany obj) {
        return airCompanyRepository.save(obj);
    }

    @Override
    public void deleteById(Long id) {
        airCompanyRepository.deleteById(id);
    }

    @Override
    public AirCompany update(AirCompany updatedCompany, Long id) {
        return airCompanyRepository.findById(id)
                .map(airCompany -> {
                    airCompany.setCompanyType(updatedCompany.getCompanyType());
                    airCompany.setName(updatedCompany.getName());
                    airCompany.setFoundedAt(updatedCompany.getFoundedAt());
                    return airCompanyRepository.save(airCompany);
                })
                .orElseThrow(() -> new AirCompanyNotFoundException(id));
    }


    @Override
    public List<Flight> findFlightsByStatus(String name, FlightStatus status) {
        AirCompany company = airCompanyRepository.findByName(name)
                .orElseThrow(() -> new AirCompanyNotFoundException(name));

        return company.getFlights().stream()
                .filter(flight -> flight.getFlightStatus().equals(status))
                .collect(Collectors.toList());

    }
}
