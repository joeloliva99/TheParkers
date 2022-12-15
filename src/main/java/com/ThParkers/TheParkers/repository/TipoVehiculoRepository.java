package com.ThParkers.TheParkers.repository;
import com.ThParkers.TheParkers.model.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Integer> {
    Optional<TipoVehiculo> findTipoVehiculoByNTipoVehiculo(String nTipoVehiculo);
}
