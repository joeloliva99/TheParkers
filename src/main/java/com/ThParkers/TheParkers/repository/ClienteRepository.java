package com.ThParkers.TheParkers.repository;

import com.ThParkers.TheParkers.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findClienteByRutCliente(String rutCliente);
}
