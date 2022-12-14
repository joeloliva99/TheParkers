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
import java.util.Optional;


@RestController
@RequestMapping("/lavado")
public class LavadoController {
	
	@Autowired
    LavadoService lavadoService;
	
	public LavadoController(LavadoService lavadoService) {
        this.lavadoService = lavadoService;
    }

    @GetMapping(value = "")
    public ResponseEntity <List<Lavado>> getAllLavados() {
        List<Lavado> lavadoList = lavadoService.buscarTodosLosLavados();
        if(!lavadoList.isEmpty()) {
        	return new ResponseEntity<>(lavadoList,HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lavado> getLavadoById(@PathVariable int id) {
        Optional<Lavado>lavadoOptional = lavadoService.findLavadoById(id);
        if(lavadoOptional.isPresent()) {
        	return new ResponseEntity<>(lavadoOptional.get(),HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
