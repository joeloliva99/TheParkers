package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.model.Boleta;
import com.ThParkers.TheParkers.repository.BoletaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoletaService {
    private BoletaRepository boletaRepository;


    public BoletaService (BoletaRepository boletaRepository) {
        this.boletaRepository = boletaRepository;
    }

    public Optional<Boleta> findBoletaByAtencion(int idAtencion) {
        return boletaRepository.findBoletaByAtencionID(idAtencion);
    }

}
