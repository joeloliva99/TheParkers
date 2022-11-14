package com.ThParkers.TheParkers.repository;

import com.ThParkers.TheParkers.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    Optional<Vehiculo> findVehiculoBypatente(String patente);
}

