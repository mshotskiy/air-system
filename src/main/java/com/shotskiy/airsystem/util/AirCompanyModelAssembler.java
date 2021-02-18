package com.shotskiy.airsystem.util;

import com.shotskiy.airsystem.controller.AirCompanyController;
import com.shotskiy.airsystem.entity.AirCompany;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AirCompanyModelAssembler implements RepresentationModelAssembler<AirCompany, EntityModel<AirCompany>> {
    @Override
    public EntityModel<AirCompany> toModel(AirCompany airCompany) {
        return EntityModel.of(airCompany,
                WebMvcLinkBuilder.linkTo(methodOn(AirCompanyController.class).find(airCompany.getCompanyId())).withSelfRel(),
                linkTo(methodOn(AirCompanyController.class).findAll()).withRel("companies")
        );
    }
}
