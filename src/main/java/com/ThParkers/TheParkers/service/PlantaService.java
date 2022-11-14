package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.repository.PlantaRepository;
import com.ThParkers.TheParkers.model.Planta;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlantaService {
    private PlantaRepository plantaRepository;

    public PlantaService (PlantaRepository plantaRepository){
        this.plantaRepository = plantaRepository;
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

}
