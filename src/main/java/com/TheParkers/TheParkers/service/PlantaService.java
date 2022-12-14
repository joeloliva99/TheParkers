package com.TheParkers.TheParkers.service;

import java.util.List;
import java.util.Optional;

import com.TheParkers.TheParkers.model.Planta;


public interface PlantaService {
	
	public List<Planta> buscarTodasLasPlantas();
	
	public Optional<Planta> findPlantaById(int id_planta);

}
