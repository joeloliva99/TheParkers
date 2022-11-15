package com.ThParkers.TheParkers.repository;
import com.ThParkers.TheParkers.model.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Integer> {
}
