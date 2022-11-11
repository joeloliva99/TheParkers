package com.TheParkers.TheParkers.service;

import java.util.List; 
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TheParkers.TheParkers.model.cliente;
import com.TheParkers.TheParkers.repository.*;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	ClienteRepository repCliente;
	
	public ClienteServiceImpl (ClienteRepository repCliente) {
        this.repCliente = repCliente;
    }

	@Override
	public List<cliente> buscarTodosLosCliente() {
		
		return repCliente.findAll();
	}

	@Override
	public cliente BuscarClientePorRUT(String rutCliente) {
		
		return repCliente.findClienteByRutCliente(rutCliente).get();
	}

	@Override
	public boolean Guardar(cliente cliente) {
		repCliente.saveAndFlush(cliente);
        Optional<com.TheParkers.TheParkers.model.cliente> clienteOptional = repCliente.findClienteByRutCliente(cliente.getRutCliente());
        return clienteOptional.isPresent();
		
	}
	
	@Override
	public Optional<cliente> findclienteByRUT(String rutCliente) {
        return repCliente.findClienteByRutCliente(rutCliente);
    }

	@Override
	public boolean BorrarClientePorId(int id) {
		Optional<cliente> clienteOptional = repCliente.findById(id);
        if (clienteOptional.isPresent()){
            repCliente.deleteById(id);
            return true;
        } else {
            return false;
        }
	}
	
}
