package com.TheParkers.TheParkers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TheParkers.TheParkers.model.Cliente;
import com.TheParkers.TheParkers.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
    ClienteService clienteService;

    @GetMapping("")
    public List<Cliente> list() {
        return clienteService.buscarTodosLosClientes();
    }
}
