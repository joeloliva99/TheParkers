package com.TheParkers.TheParkers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TheParkers.TheParkers.model.Departamento;


public interface DepartamentoRepository extends JpaRepository <Departamento, Integer>{

	Optional<Departamento> findDepartamentoById(int departamentoID);

}
