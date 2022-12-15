package com.TheParkers.TheParkers.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TheParkers.TheParkers.model.NivelPlanta;


public interface RepositorioNivelPlanta extends JpaRepository <NivelPlanta, Integer> {
	
	List<NivelPlanta> findNivelPlantaByIdPlanta(int id_planta);
	
	Optional<NivelPlanta> findNivelById(int id_nivel);

}
