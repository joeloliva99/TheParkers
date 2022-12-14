package com.TheParkers.TheParkers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TheParkers.TheParkers.model.Planta;
import com.TheParkers.TheParkers.repository.RepositorioPlanta;


@Service
@Transactional
public class PlantaServiceImpl implements PlantaService {
	
	@Autowired
	RepositorioPlanta repPlanta;
	
	public PlantaServiceImpl(RepositorioPlanta repPlanta) {
		this.repPlanta = repPlanta;
	}

	@Override
	public List<Planta> buscarTodasLasPlantas() {
		return repPlanta.findAll();
	}

	@Override
	public Optional<Planta> findPlantaById(int id_planta) {
		return repPlanta.findPlantaById(id_planta);
	}

}
