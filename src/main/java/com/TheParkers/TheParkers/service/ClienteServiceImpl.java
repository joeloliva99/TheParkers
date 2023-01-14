package com.TheParkers.TheParkers.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TheParkers.TheParkers.model.Cliente;
import com.TheParkers.TheParkers.repository.ClienteRepository;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	ClienteRepository repCliente;

	@Override
	public List<Cliente> buscarTodosLosClientes() {
		return repCliente.findAll();
	}

	@Override
	public Cliente BuscarClientePorRut(String rutCliente) {
		return repCliente.findClienteByRutCliente(rutCliente).get();
	}

	@Override
	public boolean Guardar(Cliente cliente) {
		repCliente.saveAndFlush(cliente);
		Optional<Cliente>clienteOptional = repCliente.findClienteByRutCliente(cliente.getRutCliente());
		return clienteOptional.isPresent();
	}

	@Override
	public boolean BorrarClientePorId(int id_cliente) {
		Optional<Cliente>clienteOptional = repCliente.findById(id_cliente);
		
		if(clienteOptional.isPresent()) {
			repCliente.deleteById(id_cliente);
			return true;
		}
		else {
			return false;
		}
	}

	
	
	
	
	@Override
	public Optional<Cliente> findClienteByRut(String rutCliente) {
		// TODO Auto-generated method stub
		return Optional.empty()
	}

}
