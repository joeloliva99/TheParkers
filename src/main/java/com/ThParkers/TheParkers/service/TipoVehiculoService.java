package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.repository.TipoVehiculoRepository;
import org.springframework.stereotype.Service;

@Service
public class TipoVehiculoService {
    private TipoVehiculoRepository tipoVehiculoRepository;

    public TipoVehiculoService (TipoVehiculoRepository tipoVehiculoRepository){
        this.tipoVehiculoRepository = tipoVehiculoRepository;
    }

    public int devolverTarifa (int tipo) {
        return tipoVehiculoRepository.findById(tipo).get().getTarifa();
    }
}
