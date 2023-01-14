package com.TheParkers.TheParkers.service;

import java.util.List;
import java.util.Optional;


import com.TheParkers.TheParkers.model.Dependiente;

public interface DependienteService {

	public List<Dependiente>buscarTodosLosDependientes();
	public Dependiente BuscarDependientePorRut(String rutDependiente);
	public boolean Guardar(Dependiente dependiente);
	public boolean BorrarDependientePorId(int id_dependiente);
	
	public Optional<Dependiente> findDependienteByRut(String rutDependiente);
}
