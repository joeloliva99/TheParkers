package com.TheParkers.TheParkers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TheParkers.TheParkers.model.cliente;

import java.util.Optional;


@Repository
public interface ClienteRepository extends JpaRepository<cliente, Integer> {
	
	Optional<cliente> findClienteByNombre(String nombre);
	Optional<cliente> findClienteByRutCliente(String rutCliente);

}
