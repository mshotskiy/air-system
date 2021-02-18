package com.shotskiy.airsystem.controller;


import com.shotskiy.airsystem.entity.Flight;
import com.shotskiy.airsystem.service.FlightService;
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
@RequestMapping("flight")
public class FlightController {
    private final FlightService flightService;
    private final FlightModelAssembler modelAssembler;

    public FlightController(FlightService flightService, FlightModelAssembler modelAssembler) {
        this.flightService = flightService;
        this.modelAssembler = modelAssembler;
    }

    @GetMapping("{id}")
    public EntityModel<Flight> find(@PathVariable Long id) {
        return modelAssembler.toModel(flightService.get(id));
    }

    @GetMapping
    public CollectionModel<EntityModel<Flight>> findAll() {
        List<EntityModel<Flight>> flightModels = flightService.getAll().stream()
                .map(modelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(flightModels,
                linkTo(methodOn(FlightController.class).findAll()).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Flight flight) {
        EntityModel<Flight> flightModel = modelAssembler.toModel(flightService.save(flight));

        return ResponseEntity
                .created(flightModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(flight);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Flight flight, @PathVariable Long id) {
        EntityModel<Flight> flightModel = modelAssembler
                .toModel(flightService.update(flight, id));

        return ResponseEntity
                .created(flightModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(flightModel);
    }

    @GetMapping("/longActive")
    public CollectionModel<EntityModel<Flight>> allLongActiveFlights(){
        List<EntityModel<Flight>> flightModels = flightService.getLongActiveFlights().stream()
                .map(modelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(flightModels,
                linkTo(methodOn(FlightController.class).allLongActiveFlights()).withSelfRel());
    }


    @DeleteMapping({"{id}"})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        flightService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/active")
    public ResponseEntity<?> active(@PathVariable Long id) {
        return null;
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id) {
        return null;
    }

    @PutMapping("/{id}/delayed")
    public ResponseEntity<?> delayed(@PathVariable Long id) {
        return null;
    }

    @PutMapping("/{id}/pending")
    public ResponseEntity<?> pending(@PathVariable Long id) {
        return null;
    }

}
