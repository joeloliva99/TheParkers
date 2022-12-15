package com.TheParkers.TheParkers.service;

import java.util.List;

import com.TheParkers.TheParkers.model.NivelPlanta;


public interface NivelPlantaService {
	
	public List<NivelPlanta> buscarTodosLosNivelesDePlantaSegunID(int id_planta);
	
	public boolean creaNuevoNivel(NivelPlanta nivel);

}
