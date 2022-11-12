package com.TheParkers.TheParkers.service;

import java.util.List;
import com.TheParkers.TheParkers.model.Lavado;


public interface LavadoService {
	
	public List<Lavado> buscarTodosLosLavados();
	
	public Lavado BuscarLavadoPorId(int id);
	

}
