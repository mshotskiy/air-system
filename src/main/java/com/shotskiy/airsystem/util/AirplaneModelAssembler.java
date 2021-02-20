package com.shotskiy.airsystem.util;

import com.shotskiy.airsystem.controller.AirplaneController;
import com.shotskiy.airsystem.entity.Airplane;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AirplaneModelAssembler implements RepresentationModelAssembler<Airplane, EntityModel<Airplane>> {
    @Override
    public EntityModel<Airplane> toModel(Airplane airplane) {
        return EntityModel.of(airplane,
                linkTo(methodOn(AirplaneController.class).find(airplane.getAirplaneId())).withSelfRel(),
                linkTo(methodOn(AirplaneController.class).findAll()).withRel("airplanes")
        );
    }

}
