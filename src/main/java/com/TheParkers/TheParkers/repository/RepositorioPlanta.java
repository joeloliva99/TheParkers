package com.TheParkers.TheParkers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TheParkers.TheParkers.model.Planta;

public interface RepositorioPlanta extends JpaRepository <Planta, Integer> {
	
	Optional<Planta> findPlantaById(int id_planta);

}
