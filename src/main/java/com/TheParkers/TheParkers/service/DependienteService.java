package com.TheParkers.TheParkers.service;

import java.util.List;
import java.util.Optional;


import com.TheParkers.TheParkers.model.Dependiente;

public interface DependienteService {

	public List<Dependiente>buscarTodosLosDependientes();
	public Optional<Dependiente> findDependienteByRutDependiente(String rutDependiente);
	public boolean GuardarDependiente(Dependiente dependiente);
	public boolean BorrarDependientePorId(int id);
	
	
}
