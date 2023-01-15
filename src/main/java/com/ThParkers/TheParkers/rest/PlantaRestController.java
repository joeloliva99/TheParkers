package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.dummy.StatusPlanta;
import com.ThParkers.TheParkers.service.PlantaService;
import com.ThParkers.TheParkers.model.Planta;
import com.ThParkers.TheParkers.dummy.PlantaTarifa;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plantas")
public class PlantaRestController {
    private PlantaService plantaService;

    public PlantaRestController (PlantaService plantaService) {
        this.plantaService = plantaService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Planta>> getAllPlantas() {
        List<Planta> plantaList = plantaService.findAllPlantas();
        if (!plantaList.isEmpty()){
            return new ResponseEntity<>(plantaList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{idPlant}")
    public ResponseEntity<Planta> getPlantaById(@PathVariable int idPlant) {
        Optional<Planta> plantaOptional = plantaService.findPlantaById(idPlant);
        if (plantaOptional.isPresent()) {
            return new ResponseEntity<>(plantaOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value = "")
    public ResponseEntity<Void> addPlanta(@RequestBody Planta planta) {
        boolean creado = plantaService.save(planta);
        if (creado) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "")
    public ResponseEntity<Void> updatePlanta(@RequestBody Planta planta) {
        boolean actualizado = plantaService.update(planta);
        if (actualizado) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/tarifa")
    public ResponseEntity<Void> updateTarifa(@RequestBody PlantaTarifa plantaTarifa) {
        boolean actualizado = plantaService.updateTarifa(plantaTarifa.getTarifa_planta(),plantaTarifa.getId_planta());
        if (actualizado) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{idPlant}/status")
    public ResponseEntity<StatusPlanta> getStatusPlantaById(@PathVariable int idPlant) {
        Optional<StatusPlanta> optionalStatusPlanta = plantaService.returnestatusById(idPlant);
        if (optionalStatusPlanta.isPresent()) {
            return new ResponseEntity<>(optionalStatusPlanta.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
