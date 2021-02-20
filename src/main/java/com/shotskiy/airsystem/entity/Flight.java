package com.shotskiy.airsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shotskiy.airsystem.model.FlightStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "flight")
public class Flight {

    @Id
    @Column(name = "flight_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    @Column(name = "flight_status")
    @Convert(converter = FlightStatus.PersistJPAConverter.class)
    private FlightStatus flightStatus;

    @ManyToOne
    @JoinColumn(name = "air_company_id")
    @JsonIgnoreProperties({"flights", "airplanes"})
    private AirCompany airCompany;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    @JsonIgnoreProperties({"airCompany", "flights"})
    private Airplane airPlane;

    @Column(name = "departure_country")
    private String departureCountry;

    @Column(name = "destination_country")
    private String destinationCountry;

    private Integer distance;

    @Column(name = "estimated_flight_time")
    private String estimatedFlightTime;

    @Column(name = "ended_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endedAt;

    @Column(name = "delay_started_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date delayStartedAt;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Column(name = "started_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startedAt;

}
