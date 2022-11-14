package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.repository.NivelRepository;
import com.ThParkers.TheParkers.model.NivelPlanta;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NivelService {
    private NivelRepository nivelRepository;

    public NivelService (NivelRepository nivelRepository) {
        this.nivelRepository = nivelRepository;
    }

    public int devolverIDNivel (int nroNivel, int idPlanta){
        List<NivelPlanta> nivelPlantaList = nivelRepository.findNivelPlantalByIdPlanta(idPlanta);
        Optional<NivelPlanta> nivelPlantaOptional = nivelPlantaList.stream().filter(p -> p.getN_nivel() == nroNivel).findFirst();
        if (nivelPlantaOptional.isPresent()){
            return nivelPlantaOptional.get().getId_nivel();
        } else {
            return 0;
        }
    }
}
