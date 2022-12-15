package com.TheParkers.TheParkers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TheParkers.TheParkers.model.NivelPlanta;
import com.TheParkers.TheParkers.service.NivelPlantaService;

@RestController
@RequestMapping("/nivelPlanta")
public class NivelPlantaController {
	
	@Autowired
	NivelPlantaService nivelService;
	
	public NivelPlantaController(NivelPlantaService nivelService) {
		this.nivelService = nivelService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<List<NivelPlanta>>getAllNivelesByIdPlanta(@PathVariable int id_planta){
		List<NivelPlanta> nivelList = nivelService.buscarTodosLosNivelesDePlantaSegunID(id_planta);
		if(!nivelList.isEmpty()) {
			return new ResponseEntity<>(nivelList, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "")
    public ResponseEntity<Void>newNivel(@RequestBody NivelPlanta nivel){
		boolean allright = nivelService.creaNuevoNivel(nivel);
		if(allright) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
