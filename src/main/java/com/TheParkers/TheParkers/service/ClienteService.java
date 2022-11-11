package com.TheParkers.TheParkers.service;

import java.util.List; 
import java.util.Optional;

import com.TheParkers.TheParkers.model.cliente;

public interface ClienteService {

	public List<cliente> buscarTodosLosCliente();
	
	public cliente BuscarClientePorRUT(String rut);
	
	public boolean Guardar(cliente cliente);
	
	public Optional<cliente> findclienteByRUT(String rutCliente);
	
	public boolean BorrarClientePorId(int id);
}
