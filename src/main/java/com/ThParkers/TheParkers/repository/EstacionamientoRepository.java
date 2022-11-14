package com.ThParkers.TheParkers.repository;

import com.ThParkers.TheParkers.model.Estacionamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstacionamientoRepository extends JpaRepository<Estacionamiento, Integer> {
    List<Estacionamiento> findEstacionamientoByIdNivel(int id_nivel);
}
