package com.ThParkers.TheParkers.repository;

import com.ThParkers.TheParkers.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Integer> {
    Optional<Boleta> findBoletaByAtencionID(int ID);
}
