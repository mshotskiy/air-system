package com.shotskiy.airsystem.util;

import com.shotskiy.airsystem.controller.FlightController;
import com.shotskiy.airsystem.entity.Flight;
import com.shotskiy.airsystem.model.FlightStatus;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class FlightModelAssembler implements RepresentationModelAssembler<Flight, EntityModel<Flight>> {

    @Override
    public EntityModel<Flight> toModel(Flight flight) {
        EntityModel<Flight> flightModel = EntityModel.of(flight,
                WebMvcLinkBuilder.linkTo(methodOn(FlightController.class).find(flight.getFlightId())).withSelfRel(),
                linkTo(methodOn(FlightController.class).findAll()).withRel("flights")
        );

        if (flight.getFlightStatus() == FlightStatus.PENDING) {
            flightModel.add(linkTo(methodOn(FlightController.class).active(flight.getFlightId())).withRel("active"));
            flightModel.add(linkTo(methodOn(FlightController.class).delayed(flight.getFlightId())).withRel("delay"));
        }
        if (flight.getFlightStatus() == FlightStatus.ACTIVE) {
            flightModel.add(linkTo(methodOn(FlightController.class).complete(flight.getFlightId())).withRel("complete"));
        }
        if (flight.getFlightStatus() == FlightStatus.DELAYED) {
            flightModel.add(linkTo(methodOn(FlightController.class).active(flight.getFlightId())).withRel("active"));
        }

        return flightModel;
    }
}
