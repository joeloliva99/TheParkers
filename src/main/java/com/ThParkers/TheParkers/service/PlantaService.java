package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.dummy.StatusPlanta;
import com.ThParkers.TheParkers.model.Estacionamiento;
import com.ThParkers.TheParkers.model.NivelPlanta;
import com.ThParkers.TheParkers.repository.EstacionamientoRepository;
import com.ThParkers.TheParkers.repository.NivelRepository;
import com.ThParkers.TheParkers.repository.PlantaRepository;
import com.ThParkers.TheParkers.model.Planta;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlantaService {
    private PlantaRepository plantaRepository;
    private NivelRepository nivelRepository;
    private EstacionamientoRepository estacionamientoRepository;

    public PlantaService (PlantaRepository plantaRepository, NivelRepository nivelRepository, EstacionamientoRepository estacionamientoRepository){
        this.plantaRepository = plantaRepository;
        this.nivelRepository = nivelRepository;
        this.estacionamientoRepository = estacionamientoRepository;
    }

    public List<Planta> findAllPlantas() {
        return plantaRepository.findAll();
    }

    public Optional<Planta> findPlantaById(int idPlant) {
        return plantaRepository.findById(idPlant);
    }

    public boolean save(Planta planta) {
        plantaRepository.saveAndFlush(planta);
        Optional<Planta> plantaOptional = plantaRepository.findPlantaByDireccion(planta.getDireccion());
        return plantaOptional.isPresent();
    }

    public boolean update(Planta planta) {
        Optional<Planta>plantaOptional = plantaRepository.findById(planta.getId_planta());
        if (plantaOptional.isPresent()) {
            plantaRepository.saveAndFlush(planta);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateTarifa(int tarifa_planta, int id_planta) {
        Optional<Planta>plantaOptional = plantaRepository.findById(id_planta);
        if (plantaOptional.isPresent()){
            Planta planta = new Planta();
            planta = plantaOptional.get();
            planta.setTarifa_planta(tarifa_planta);
            plantaRepository.saveAndFlush(planta);
           return true;
        } else {
            return false;
        }
    }

    public Optional<StatusPlanta> returnestatusById(int idPlant) {
        StatusPlanta statusPlanta = new StatusPlanta();
        statusPlanta.setIdPlanta(idPlant);
        int[] estado = devolverPlazasEstac(idPlant);
        if (estado[0] == -1) {
            return Optional.empty();
        }
        statusPlanta.setNroNiveles(estado[0]);
        statusPlanta.setTotalEstac(estado[1]);
        statusPlanta.setProporLleno(String.valueOf(estado[2]) + "/" + String.valueOf(estado[1]));
        String statusTex = "Vacío";
        if (estado[3] > 0) {
            statusTex = "OK";
        }
        if (estado[3] >= 80) {
            statusTex = "Casi lleno";
        }
        if (estado[3] >= 100) {
            statusTex = "Lleno";
        }
        statusPlanta.setEstado(statusTex);
        Optional<StatusPlanta> statusPlantaOptional = Optional.of(statusPlanta);
        return statusPlantaOptional;
    }

    // Función que retorna la tarifa actual según qué tan llena esté la planta
    //  < 50 % de estacionamiento ocupados --> tarifa normal
    // >= 50 % de estacionamiento ocupados -- > 20% de incremento en la tarifa
    // >= 70 % de estacionamiento ocupados -- > 30% de incremento en la tarifa
    // >= 90 % de estacionamiento ocupados -- > 50% de incremento en la tarifa
    public int getTarifaActual (int idPlant, int tarifaNormal) {
        int[] estado = devolverPlazasEstac(idPlant);
        if (estado[0] == -1) {
            return -1;
        }
        int porInc = 0;
        if (estado[3] >= 50) {
            porInc = 20;
        }
        if (estado[3] >= 70) {
            porInc = 30;
        }
        if (estado[3] >= 90) {
            porInc = 50;
        }
        int tarifaFinal = tarifaNormal + ((tarifaNormal*porInc)/100);
        return tarifaFinal;

    }

    // Esta función retorna un arreglo de la siguiente manera:
    // devolverPlazasEstac[0] = Numero de niveles dentro de la planta
    // devolverPlazasEstac[1] = Numero total de estacionamientos dentro de una planta
    // devolverPlazasEstac[2] = Numero de estacionamientos ocupados dentro de una planta (no disponibles)
    // devolverPlazasEstac[3] = El porcentaje de ocupación del estacionamiento
    // Si en el índice 0 retorna -1, significa que no halló estacionamientos en la planta dada
    public int[] devolverPlazasEstac (int idPlant) {
        int[] devolver = new int[4];
        List<NivelPlanta> nivelPlantaList = nivelRepository.findNivelPlantalByIdPlanta(idPlant);
        if (nivelPlantaList.isEmpty()) {
            devolver[0] = -1;
            return devolver;
        }
        devolver[0] = nivelPlantaList.size();
        int nroEstacionamiento = 0;
        int nroEstacionamientoNotAvailable = 0;
        for (int i = 0; i < nivelPlantaList.size(); i ++) {
            List<Estacionamiento> estacionamientoList = estacionamientoRepository.findEstacionamientoByIdNivel(nivelPlantaList.get(i).getId_nivel());
            nroEstacionamiento+= estacionamientoList.size();
            List<Estacionamiento> disponibles = estacionamientoList.stream()
                    .filter(p -> p.isDisponible() == false).collect(Collectors.toList());
            nroEstacionamientoNotAvailable+= disponibles.size();
        }
        int porcentajeLleno = 0;
        porcentajeLleno = ((100*nroEstacionamientoNotAvailable)/nroEstacionamiento);
        devolver[1] = nroEstacionamiento;
        devolver[2] = nroEstacionamientoNotAvailable;
        devolver[3] = porcentajeLleno;

        return devolver;
    }


}
