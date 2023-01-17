package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.model.Atencion;
import com.ThParkers.TheParkers.model.Boleta;
import com.ThParkers.TheParkers.repository.BoletaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoletaServiceTest {
    @Mock
    private BoletaRepository boletaRepository;
    @InjectMocks
    private BoletaService boletaService;

    @Test
    public void siInvocoFindfindBoletaByAtencionDebeRetornarOptionalConBoleta(){
        // Arrange
        Boleta boleta = getBoletas().get(0);
        Atencion atencion = getAtenciones().get(0);
        Optional<Boleta> boletaOptional = Optional.of(boleta);
        when(boletaRepository.findBoletaByAtencionID(atencion.getAtencionID())).thenReturn(boletaOptional);

        // Act
        Optional<Boleta> resultado = boletaService.findBoletaByAtencion(atencion.getAtencionID());

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isPresent());
    }



    private List<Boleta> getBoletas() {
        List<Boleta> boletaArrayList = new ArrayList<>();
        Boleta boleta = new Boleta();
        boleta.setId_boleta(1);
        boleta.setAtencionID(1);
        boleta.setSubtotal(1000);
        boleta.setDescuento(100);
        boleta.setTotal(900);
        boletaArrayList.add(boleta);
        boleta = new Boleta();
        boleta.setId_boleta(2);
        boleta.setAtencionID(2);
        boleta.setSubtotal(5000);
        boleta.setDescuento(100);
        boleta.setTotal(4900);
        boletaArrayList.add(boleta);
        return boletaArrayList;
    }

    private List<Atencion> getAtenciones() {
        List<Atencion> atencionList = new ArrayList<>();
        Atencion atencion = new Atencion();
        atencion.setAtencionID(1);
        atencion.setIdCliente(1);
        atencion.setHoraEntrada("2023-01-16 13:31");
        atencion.setHoraSalida("2023-01-16 16:31");
        atencion.setId_vehiculo(1);
        atencion.setId_empleado(1);
        atencion.setId_estacionamiento(1);
        atencion.setHay_lavado(false);
        atencion.setId_lavado(null);
        atencionList.add(atencion);
        atencion = new Atencion();
        atencion.setAtencionID(2);
        atencion.setIdCliente(1);
        atencion.setHoraEntrada("2023-01-17 13:31");
        atencion.setHoraSalida("2023-01-17 16:31");
        atencion.setId_vehiculo(1);
        atencion.setId_empleado(1);
        atencion.setId_estacionamiento(1);
        atencion.setHay_lavado(false);
        atencion.setId_lavado(null);
        atencionList.add(atencion);
        return atencionList;
    }
}



