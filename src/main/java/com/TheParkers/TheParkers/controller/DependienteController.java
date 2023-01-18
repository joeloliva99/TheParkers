package com.TheParkers.TheParkers.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.TheParkers.TheParkers.model.Dependiente;
import com.TheParkers.TheParkers.service.DependienteService;

@RestController
@RequestMapping("/dependiente")
public class DependienteController {
	
	@Autowired
	DependienteService dependienteService;

	public DependienteController(DependienteService dependienteService) {
		this.dependienteService = dependienteService;
	}
	
    @GetMapping(value = "")
    public ResponseEntity <List<Dependiente>>getAllDependientes() {
    	List<Dependiente> dependienteList = dependienteService.buscarTodosLosDependientes();
        if (!dependienteList.isEmpty()) {
            return new ResponseEntity<>(dependienteList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
	@GetMapping(value = "")
    public ResponseEntity<Dependiente>getDependienteByRut(@PathVariable String rutDependiente){
		Optional<Dependiente>dependienteOptional = dependienteService.findDependienteByRutDependiente(rutDependiente);
		if(dependienteOptional.isPresent()) {
			return new ResponseEntity<>(dependienteOptional.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
    @PostMapping(value = "")
    public ResponseEntity<Void> newDependiente(@RequestBody Dependiente dependiente) {
        boolean allright = dependienteService.GuardarDependiente(dependiente);
        if (allright){
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping(value = "")
    public ResponseEntity<Void> deleteDependienteByID(@PathVariable int idClient){
        boolean eliminado = dependienteService.BorrarDependientePorId(idClient);
        if (eliminado){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	
	
    
 
}
