package com.TheParkers.TheParkers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TheParkers.TheParkers.model.Planta;
import com.TheParkers.TheParkers.service.PlantaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/planta")
public class PlantaController {
	
	@Autowired
	PlantaService plantaService;
	
	public PlantaController(PlantaService plantaService) {
		this.plantaService = plantaService;
	}
	
	@GetMapping(value = "")
	public ResponseEntity<List<Planta>>getAllPlantas(){
		List<Planta> plantaList = plantaService.buscarTodasLasPlantas();
		if(!plantaList.isEmpty()) {
			return new ResponseEntity<>(plantaList, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Planta>getPlantaById(@PathVariable int id){
		Optional<Planta>plantaOptional = plantaService.findPlantaById(id);
		if(plantaOptional.isPresent()) {
			return new ResponseEntity<>(plantaOptional.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
