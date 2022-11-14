package com.ThParkers.TheParkers.repository;

import com.ThParkers.TheParkers.model.NivelPlanta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NivelRepository extends JpaRepository<NivelPlanta, Integer> {
    List<NivelPlanta> findNivelPlantalByIdPlanta(int id_planta);
}

