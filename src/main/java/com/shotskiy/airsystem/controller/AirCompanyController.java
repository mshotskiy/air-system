package com.shotskiy.airsystem.controller;

import com.shotskiy.airsystem.model.FlightStatus;
import com.shotskiy.airsystem.entity.AirCompany;
import com.shotskiy.airsystem.entity.Flight;
import com.shotskiy.airsystem.service.AirCompanyService;
import com.shotskiy.airsystem.util.AirCompanyModelAssembler;
import com.shotskiy.airsystem.util.FlightModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("company")
public class AirCompanyController {
    private final AirCompanyService companyService;
    private final AirCompanyModelAssembler airCompanyModelAssembler;
    private final FlightModelAssembler flightModelAssembler;

    public AirCompanyController(AirCompanyService companyService, AirCompanyModelAssembler airCompanyModelAssembler, FlightModelAssembler flightModelAssembler) {
        this.companyService = companyService;
        this.airCompanyModelAssembler = airCompanyModelAssembler;
        this.flightModelAssembler = flightModelAssembler;
    }

    @GetMapping("/{id}")
    public EntityModel<AirCompany> find(@PathVariable Long id) {
        AirCompany airCompany = companyService.get(id);
        return airCompanyModelAssembler.toModel(airCompany);
    }

    @GetMapping
    public CollectionModel<EntityModel<AirCompany>> findAll() {
        List<EntityModel<AirCompany>> airCompanies = companyService.getAll().stream()
                .map(airCompanyModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(airCompanies,
                linkTo(methodOn(AirCompanyController.class).findAll()).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody AirCompany company) {
        EntityModel<AirCompany> airCompanyModel = airCompanyModelAssembler.toModel(companyService.save(company));
        return ResponseEntity
                .created(airCompanyModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(airCompanyModel);
    }

    @PutMapping({"{id}"})
    public ResponseEntity<?> update(@RequestBody AirCompany company, @PathVariable Long id) {
        EntityModel<AirCompany> airCompanyEntityModel = airCompanyModelAssembler.toModel(companyService.update(company, id));
        return ResponseEntity
                .created(airCompanyEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(airCompanyEntityModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{name}/flights")
    public CollectionModel<EntityModel<Flight>> flightsByStatus(@PathVariable String name, @RequestParam FlightStatus status) {
        List<EntityModel<Flight>> flights = companyService.findFlightsByStatus(name, status).stream()
                .map(flightModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(flights,
                linkTo(methodOn(AirCompanyController.class).flightsByStatus(name, status)).withSelfRel());
    }

}
