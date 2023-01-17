package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.TheParkersApplication;
import com.ThParkers.TheParkers.repository.EstacionamientoRepository;
import com.ThParkers.TheParkers.model.Estacionamiento;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstacionamientoService {
    private EstacionamientoRepository estacionamientoRepository;
    private NivelService nivelService;

    public EstacionamientoService (EstacionamientoRepository estacionamientoRepository, NivelService nivelService) {
        this.estacionamientoRepository = estacionamientoRepository;
        this.nivelService = nivelService;
    }

    public List<Estacionamiento> findAllEstacionamientos(){
        return estacionamientoRepository.findAll();
    }

    public List<Estacionamiento> estacionamientosDisponiblesPorNivel(int nroNivel) {
        int idPlanta = TheParkersApplication.idPlanta;
        int id_nivel = nivelService.devolverIDNivel(nroNivel,idPlanta);
        List<Estacionamiento> disponibles = findAllEstacionamientosByPlantaNivel(id_nivel).stream()
                .filter(p -> p.isDisponible() == true).collect(Collectors.toList());
        return disponibles;
    }

    public List<Estacionamiento> findAllEstacionamientosByPlantaNivel(int id_nivel) {
        return estacionamientoRepository.findEstacionamientoByIdNivel(id_nivel);
    }

    public int devolverIdEstacionamiento(int id_planta, int NroNivel, int nroEstacionamiento) {
        int idNivel = nivelService.devolverIDNivel(NroNivel, id_planta);
        List<Estacionamiento> estacionamientoList = estacionamientoRepository.findEstacionamientoByIdNivel(idNivel);
        Optional<Estacionamiento> estacionamientoOptional = estacionamientoList.stream().filter(p -> p.getNro_estacionamiento() == nroEstacionamiento).findFirst();
        if (estacionamientoOptional.isPresent()){
            return estacionamientoOptional.get().getId_estacionamiento();
        } else {
            return 0;
        }
    }

    public boolean isAvailable (int idEstacionamiento){
        return estacionamientoRepository.findById(idEstacionamiento).get().isDisponible();
    }

    public boolean ocuparInutilizarEstacionamiento (int idEstacionamiento) {
        if (isAvailable(idEstacionamiento)) {
            estacionamientoRepository.findById(idEstacionamiento).get().setDisponible(false);
            return true;
        } else {
            return false;
        }
    }

    public boolean disponibilizarEstacionamiento (int idEstacionamiento) {
        if (!isAvailable(idEstacionamiento)) {
            estacionamientoRepository.findById(idEstacionamiento).get().setDisponible(true);
            return true;
        } else {
            return false;
        }
    }
}
