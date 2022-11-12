package com.TheParkers.TheParkers.controller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TheParkers.TheParkers.model.Lavado;
import com.TheParkers.TheParkers.service.LavadoService;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/lavado")
public class LavadoController {
	
	@Autowired
    LavadoService lavadoService;
	
	public LavadoController(LavadoService lavadoService) {
        this.lavadoService = lavadoService;
    }

    @GetMapping(value = "")
    public List<Lavado> list() {
        return lavadoService.buscarTodosLosLavados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lavado> get(@PathVariable int id) {
        try {
            Lavado lavado = lavadoService.BuscarLavadoPorId(id);
            return new ResponseEntity<Lavado>(lavado, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Lavado>(HttpStatus.NOT_FOUND);
        }
    }

}
