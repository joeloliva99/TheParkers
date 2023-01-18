package com.TheParkers.TheParkers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TheParkers.TheParkers.model.*;

public interface DependienteRepository extends JpaRepository <Dependiente, Integer> {
	Optional<Dependiente> findDependienteByRutDependiente(String rutDependiente);
}
