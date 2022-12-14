package com.TheParkers.TheParkers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.TheParkers.TheParkers.model.Lavado;


public interface RepositorioLavado extends JpaRepository <Lavado, Integer> {
	
	Optional<Lavado> findLavadoById(int id_lavado);
	
}
