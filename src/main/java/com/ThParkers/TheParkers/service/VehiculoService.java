package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.model.Vehiculo;
import com.ThParkers.TheParkers.repository.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {
    private VehiculoRepository vehiculoRepository;

    public VehiculoService (VehiculoRepository vehiculoRepository) {

        this.vehiculoRepository = vehiculoRepository;
    }

    public List<Vehiculo> findAllVehiculos() {
        return vehiculoRepository.findAll();
    }


    public boolean save(Vehiculo vehiculo) {
        vehiculoRepository.saveAndFlush(vehiculo);
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findVehiculoBypatente(vehiculo.getPatente());
        return vehiculoOptional.isPresent();
    }

    public Optional<Vehiculo> findVehiculoBypatente(String patente) {
        return vehiculoRepository.findVehiculoBypatente(patente);
    }

    public boolean deleteVehiculoById(int idVehiculo) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findById(idVehiculo);
        if (vehiculoOptional.isPresent()){
            vehiculoRepository.deleteById(idVehiculo);
            return true;
        } else {
            return false;
        }
    }
}
