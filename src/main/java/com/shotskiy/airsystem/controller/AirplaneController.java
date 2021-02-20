package com.shotskiy.airsystem.controller;

import com.shotskiy.airsystem.entity.AirCompany;
import com.shotskiy.airsystem.entity.Airplane;
import com.shotskiy.airsystem.service.AirplaneService;
import com.shotskiy.airsystem.util.AirplaneModelAssembler;
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
@RequestMapping("airplane")
public class AirplaneController {
    private final AirplaneService airplaneService;
    private final AirplaneModelAssembler airplaneModelAssembler;

    public AirplaneController(AirplaneService airplaneService, AirplaneModelAssembler airplaneModelAssembler) {
        this.airplaneService = airplaneService;
        this.airplaneModelAssembler = airplaneModelAssembler;
    }

    @GetMapping("/{id}")
    public EntityModel<Airplane> find(@PathVariable Long id) {
        return airplaneModelAssembler.toModel(airplaneService.get(id));
    }

    @GetMapping
    public CollectionModel<EntityModel<Airplane>> findAll() {
        List<EntityModel<Airplane>> airplanes = airplaneService.getAll().stream()
                .map(airplaneModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(airplanes,
                linkTo(methodOn(AirplaneController.class).findAll()).withSelfRel());
    }


    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody Airplane airplane) {
        EntityModel<Airplane> airplaneEntityModel = airplaneModelAssembler
                .toModel(airplaneService.save(airplane));

        return ResponseEntity
                .created(airplaneEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(airplaneEntityModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Airplane airplane, @PathVariable Long id) {
        EntityModel<Airplane> airplaneModel = airplaneModelAssembler.toModel(airplaneService.update(airplane, id));

        return ResponseEntity
                .created(airplaneModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(airplaneModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        airplaneService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/airCompany")
    public ResponseEntity<?> moveAirplane(@RequestBody AirCompany airCompany, @PathVariable Long id) {
        EntityModel<Airplane> airplaneModel = airplaneModelAssembler
                .toModel(airplaneService.changeCompany(airCompany, id));

        return ResponseEntity
                .created(airplaneModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(airplaneModel);
    }
}
