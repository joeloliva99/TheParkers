package com.TheParkers.TheParkers.service;

import java.util.List;
import java.util.Optional;

import com.TheParkers.TheParkers.model.Cliente;

public interface ClienteService {

	public List<Cliente>buscarTodosLosClientes();
	public Cliente BuscarClientePorRut(String rutCliente);
	public boolean Guardar(Cliente cliente);
	public boolean BorrarClientePorId(int id_cliente);
	
	//..
	public Optional<Cliente> findClienteByRut(String rutCliente);
}
