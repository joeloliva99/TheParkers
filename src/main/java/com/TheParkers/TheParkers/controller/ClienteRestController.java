package com.TheParkers.TheParkers.controller;

import java.util.List; 

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TheParkers.TheParkers.model.cliente;
import com.TheParkers.TheParkers.service.ClienteService;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class ClienteRestController {
	
	@Autowired
    ClienteService clienteService;
	

    public ClienteRestController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping(value = "")
    public List<cliente> list() {
        return clienteService.buscarTodosLosCliente();
    }
    
    @GetMapping(value = "/{RUTCliente}")
    public ResponseEntity<cliente> getClienteByRUT(@PathVariable String RUTCliente) {
        Optional<cliente> clienteOptional = clienteService.findclienteByRUT(RUTCliente);
        if (clienteOptional.isPresent()) {
            return new ResponseEntity<>(clienteOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping(value = "")
    public ResponseEntity<Void> newCliente(@RequestBody cliente cliente) {
        boolean allright = clienteService.Guardar(cliente);
        if (allright){
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    
    @DeleteMapping(value = "/{idClient}")
    public ResponseEntity<Void> deleteClienteByID(@PathVariable int idClient){
        boolean eliminado = clienteService.BorrarClientePorId(idClient);
        if (eliminado){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
