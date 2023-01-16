package com.TheParkers.TheParkers.controller;

import java.util.List;

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

import com.TheParkers.TheParkers.model.Departamento;
import com.TheParkers.TheParkers.service.DepartamentoService;

@RestController
@RequestMapping("/departamento")
public class DepartamentoController {

	@Autowired
	DepartamentoService departamentoService;

	public DepartamentoController(DepartamentoService departamentoService) {
		this.departamentoService = departamentoService;
	}

	@GetMapping(value = "")
	public ResponseEntity<List<Departamento>>getAllDepartamentos(){
		List<Departamento> departamentoList = departamentoService.buscarTodosLosDepartamentos();
		if(!departamentoList.isEmpty()) {
			return new ResponseEntity<>(departamentoList, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@PostMapping(value = "")
    public ResponseEntity<Void>newDepartamento(@RequestBody Departamento departamento){
		boolean allright = departamentoService.GuardarDepartamento(departamento);
		if(allright) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
    @DeleteMapping(value = "/{departamentoID}")
    public ResponseEntity<Void> deleteDepartamentoByID(@PathVariable int departamentoID){
        boolean eliminado = departamentoService.BorrarDepartamentoPorId(departamentoID);
        if (eliminado){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }	
}
