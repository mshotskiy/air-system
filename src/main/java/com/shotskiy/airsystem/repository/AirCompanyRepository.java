package com.shotskiy.airsystem.repository;

import com.shotskiy.airsystem.entity.AirCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirCompanyRepository extends JpaRepository<AirCompany, Long> {
    Optional<AirCompany> findByName(String name);
}
