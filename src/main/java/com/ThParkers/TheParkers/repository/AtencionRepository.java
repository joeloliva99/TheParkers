package com.ThParkers.TheParkers.repository;

import com.ThParkers.TheParkers.model.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Integer> {
    Optional<Atencion> findAtencionByHoraEntrada(String horaEntrada);
    List<Atencion> findAtencionByIdCliente(int idCliente);
}
