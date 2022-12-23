package com.ThParkers.TheParkers.repository;

import com.ThParkers.TheParkers.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findEmpleadoByRutEmpleado(String rutCliente);
    List<Empleado> findEmpleadoByActivo(boolean activo);
}
