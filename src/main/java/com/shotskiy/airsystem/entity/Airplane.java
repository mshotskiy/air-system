package com.shotskiy.airsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "airplane")
public class Airplane {
    @Id
    @Column(name = "airplane_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airplaneId;

    private String name;

    @Column(name = "factory_serial_number")
    private String factorySerialNumber;

    @ManyToOne
    @JoinColumn(name = "air_company_id")
    @JsonIgnoreProperties({"airplanes", "flights"})
    private AirCompany airCompany;

    @Column(name = "number_of_flights")
    private Integer numberOfFlights;

    @Column(name = "flight_distance")
    private Long flightDistance;

    @Column(name = "fuel_capacity")
    private Integer fuelCapacity;

    private String type;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "airPlane", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("airPlane")
    private List<Flight> flights;
}
