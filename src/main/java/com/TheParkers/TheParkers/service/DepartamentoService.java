package com.TheParkers.TheParkers.service;

import java.util.List;
import com.TheParkers.TheParkers.model.Departamento;


public interface DepartamentoService {
	public List<Departamento>buscarTodosLosDepartamentos();
	public Departamento BuscarDepartamentoPorId(int id);
	
	
	
	
	public boolean GuardarDepartamento(Departamento departamento);
	
	public boolean BorrarDepartamentoPorId(int id);
}
