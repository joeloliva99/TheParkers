package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.model.Boleta;
import com.ThParkers.TheParkers.service.BoletaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/boletas")
public class BoletaRestController {

    private BoletaService boletaService;

    public BoletaRestController(BoletaService boletaService){
        this.boletaService = boletaService;
    }

    @GetMapping(value = "/{idAtencion}")
    public ResponseEntity<Boleta> getBoletaByIdAtencion(@PathVariable int idAtencion) {
        Optional<Boleta> boletaOptional = boletaService.findBoletaByAtencion(idAtencion);
        if (boletaOptional.isPresent()) {
            return new ResponseEntity<>(boletaOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
