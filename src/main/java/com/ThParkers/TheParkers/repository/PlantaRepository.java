package com.ThParkers.TheParkers.repository;

import com.ThParkers.TheParkers.model.Planta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantaRepository extends JpaRepository<Planta, Integer> {
    Optional<Planta> findPlantaByDireccion(String direccion);
}
