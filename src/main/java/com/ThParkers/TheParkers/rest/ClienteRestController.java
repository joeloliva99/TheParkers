package com.ThParkers.TheParkers.rest;
import com.ThParkers.TheParkers.model.Cliente;
import com.ThParkers.TheParkers.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteRestController {
    private ClienteService clienteService;

    public ClienteRestController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clienteList = clienteService.findAllClientes();
        if (!clienteList.isEmpty()){
            return new ResponseEntity<>(clienteList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Void> newCliente(@RequestBody Cliente cliente) {
        boolean allright = clienteService.save(cliente);
        if (allright){
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{RUTCliente}")
    public ResponseEntity<Cliente> getClienteByRUT(@PathVariable String RUTCliente) {
        Optional<Cliente> clienteOptional = clienteService.findClienteByRUT(RUTCliente);
        if (clienteOptional.isPresent()) {
            return new ResponseEntity<>(clienteOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{idClient}")
    public ResponseEntity<Void> deleteClienteByID(@PathVariable int idClient){
        boolean eliminado = clienteService.deleteClienteById(idClient);
        if (eliminado){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
