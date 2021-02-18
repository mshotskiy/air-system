package com.shotskiy.airsystem.repository;

import com.shotskiy.airsystem.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("select f from Flight f where f.flightStatus = 'Active'")
    List<Flight> findAllActive();

    @Query("select f from Flight f where f.flightStatus = 'Completed'")
    List<Flight> findAllCompleted();
}
