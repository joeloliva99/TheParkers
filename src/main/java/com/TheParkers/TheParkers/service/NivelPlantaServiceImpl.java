package com.TheParkers.TheParkers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TheParkers.TheParkers.model.NivelPlanta;
import com.TheParkers.TheParkers.repository.RepositorioNivelPlanta;

@Service
@Transactional
public class NivelPlantaServiceImpl implements NivelPlantaService {
	
	@Autowired
	RepositorioNivelPlanta repNivel;
	
	public NivelPlantaServiceImpl(RepositorioNivelPlanta repNivel) {
		this.repNivel = repNivel;
	}

	@Override
	public List<NivelPlanta> buscarTodosLosNivelesDePlantaSegunID(int id_planta) {
		return repNivel.findNivelPlantaByIdPlanta(id_planta);
	}

	@Override
	public boolean creaNuevoNivel(NivelPlanta nivel) {
		repNivel.saveAndFlush(nivel);
		Optional<NivelPlanta> nivelOptional = repNivel.findNivelById(nivel.getId_nivel());
		
		if(nivelOptional.isEmpty()) {
			repNivel.saveAndFlush(nivel);
			return nivelOptional.isPresent();
		}else {
			return false;
		}
	}

}
