package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.model.TipoVehiculo;
import com.ThParkers.TheParkers.model.Vehiculo;
import com.ThParkers.TheParkers.repository.TipoVehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoVehiculoService {
    private TipoVehiculoRepository tipoVehiculoRepository;

    public TipoVehiculoService (TipoVehiculoRepository tipoVehiculoRepository){
        this.tipoVehiculoRepository = tipoVehiculoRepository;
    }

    public int devolverTarifa (int tipo) {
        return tipoVehiculoRepository.findById(tipo).get().getTarifa();
    }

    public List<TipoVehiculo> findAllTiposVehiculos() {
        return tipoVehiculoRepository.findAll();
    }

    public boolean save(TipoVehiculo tipoVehiculo) {
        tipoVehiculoRepository.saveAndFlush(tipoVehiculo);
        Optional<TipoVehiculo> vehiculoOptional = tipoVehiculoRepository.findTipoVehiculoByNTipoVehiculo(tipoVehiculo.getNTipoVehiculo());
        return vehiculoOptional.isPresent();
    }

    public boolean deleteTipoVehiculoById(int idTipoVehiculo) {
        Optional<TipoVehiculo> tipoVehiculoOptional = tipoVehiculoRepository.findById(idTipoVehiculo);
        if (tipoVehiculoOptional.isPresent()){
            tipoVehiculoRepository.deleteById(idTipoVehiculo);
            return true;
        } else {
            return false;
        }
    }

    public Optional<TipoVehiculo> findTipoVehiculoById(int idTipo) {
        return tipoVehiculoRepository.findById(idTipo);
    }

    public boolean update(TipoVehiculo tipoVehiculo) {
        Optional<TipoVehiculo> tipoVehiculoOptional = tipoVehiculoRepository.findById(tipoVehiculo.getId_tipoVehiculo());
        if (tipoVehiculoOptional.isPresent()) {
            tipoVehiculoRepository.saveAndFlush(tipoVehiculo);
            return true;
        } else {
            return false;
        }
    }
}
