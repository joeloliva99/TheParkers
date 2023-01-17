package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.model.NivelPlanta;
import com.ThParkers.TheParkers.repository.NivelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NivelServiceTest {
    @Mock
    private NivelRepository nivelRepository;
    @InjectMocks
    private NivelService nivelService;


    @Test
    public void siInvocoDevolverIDNivelDebeRetornarIntConIDDelNivel(){
        // Arrange
        List<NivelPlanta> nivelPlantaList = getNivelPlantas();
        when(nivelRepository.findNivelPlantalByIdPlanta(2)).thenReturn(nivelPlantaList);

        // Act
        int resultado = nivelService.devolverIDNivel(3,2);

        // Assert
        assertNotNull(resultado);
        assertEquals(resultado, 1);
    }

    @Test
    public void siInvocoDevolverIDNivelYNoExisteTalNivelDebeRetornar0(){
        // Arrange
        List<NivelPlanta> nivelPlantaList = List.of();
        when(nivelRepository.findNivelPlantalByIdPlanta(2)).thenReturn(nivelPlantaList);

        // Act
        int resultado = nivelService.devolverIDNivel(3,2);

        // Assert
        assertNotNull(resultado);
        assertEquals(resultado, -1);
    }


    private List<NivelPlanta> getNivelPlantas() {
        List<NivelPlanta> nivelPlantaList = new ArrayList<>();
        NivelPlanta nivelPlanta = new NivelPlanta();
        nivelPlanta.setId_nivel(1);
        nivelPlanta.setN_nivel(3);
        nivelPlanta.setIdPlanta(2);
        nivelPlantaList.add(nivelPlanta);
        nivelPlanta = new NivelPlanta();
        nivelPlanta.setId_nivel(2);
        nivelPlanta.setN_nivel(1);
        nivelPlanta.setIdPlanta(2);
        nivelPlantaList.add(nivelPlanta);
        return nivelPlantaList;
    }
}
