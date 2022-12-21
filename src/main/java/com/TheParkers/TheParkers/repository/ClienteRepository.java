package com.TheParkers.TheParkers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TheParkers.TheParkers.model.Cliente;

public interface ClienteRepository extends JpaRepository <Cliente, Integer>  {
	
	//...
	Optional<Cliente> findClienteByNombre(String nombre);
	Optional<Cliente> findClienteByRutCliente(String rutCliente);
}
