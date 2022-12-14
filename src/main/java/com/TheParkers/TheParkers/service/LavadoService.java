package com.TheParkers.TheParkers.service;

import java.util.List;
import java.util.Optional;

import com.TheParkers.TheParkers.model.Lavado;


public interface LavadoService {
	
	public List<Lavado> buscarTodosLosLavados();
	
	public Lavado BuscarLavadoPorId(int id);
	
	public Optional<Lavado> findLavadoById(int id_lavado);
	
}
