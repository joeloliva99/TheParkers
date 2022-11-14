package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.model.Estacionamiento;
import com.ThParkers.TheParkers.service.EstacionamientoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estacionamientos")
public class EstacionamientoRestController {

    private EstacionamientoService estacionamientoService;

    public EstacionamientoRestController(EstacionamientoService estacionamientoService) {
        this.estacionamientoService= estacionamientoService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Estacionamiento>> getAllEstacionamientos(){
        List<Estacionamiento> estacionamientoList = estacionamientoService.findAllEstacionamientos();
        if (!estacionamientoList.isEmpty()){
            return new ResponseEntity<>(estacionamientoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/nivel/{nroNivel}")
    public ResponseEntity<List<Estacionamiento>> getEstacionamientosDisponiblesPorId(@PathVariable int nroNivel){
        List<Estacionamiento> estacionamientoList = estacionamientoService.estacionamientosDisponiblesPorNivel(nroNivel);
        if (!estacionamientoList.isEmpty()){
            return new ResponseEntity<>(estacionamientoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
