package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.model.Vehiculo;
import com.ThParkers.TheParkers.repository.VehiculoRepository;
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
public class VehiculoServiceTest {
    @Mock
    private VehiculoRepository vehiculoRepository;
    @InjectMocks
    private VehiculoService vehiculoService;

    @Test
    public void siInvocoFindllVehiculosYExistenVehiculosDebeRetornarListaDeVehiculos(){
        // Arrange
        List<Vehiculo> vehiculos = getVehiculos();
        when(vehiculoRepository.findAll()).thenReturn(vehiculos);

        // Act
        List<Vehiculo> resultado = vehiculoService.findAllVehiculos();

        // Assert
        assertNotNull(resultado);
        assertEquals(vehiculos.size(),resultado.size());
        assertEquals(vehiculos.get(0),resultado.get(0));
    }

    @Test
    public void siInvocoFindAllVehiculosYNoExistenVehiculosDebeRetornarListaVacia(){
        // Arrange
        when(vehiculoRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Vehiculo> resultado = vehiculoService.findAllVehiculos();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void siInvocoSaveYSePuedeAlmacenarElObjetoDebeRetornarVerdadero(){
        // Arrange
        Vehiculo vehiculo = getVehiculos().get(0);
        Optional<Vehiculo> vehiculoOptional = Optional.of(vehiculo);
        when(vehiculoRepository.findVehiculoBypatente(vehiculo.getPatente())).thenReturn(vehiculoOptional);

        // Act
        boolean resultado = vehiculoService.save(vehiculo);

        // Assert
        assertTrue(resultado);
    }


    @Test
    public void siInvocoSaveYNoSePuedeAlmacenarElObjetoDebeRetornarFalso(){
        // Arrange
        Vehiculo vehiculo = getVehiculos().get(0);
        Optional<Vehiculo> vehiculoOptionalVacio = Optional.empty();
        when(vehiculoRepository.findVehiculoBypatente(vehiculo.getPatente())).thenReturn(vehiculoOptionalVacio);

        // Act
        boolean resultado = vehiculoService.save(vehiculo);

        // Assert
        assertFalse(resultado);
    }

    @Test
    public void siInvocoFindVehiculoBypatenteDebeRetornarOptionalConVehiculo(){
        // Arrange
        List<Vehiculo> vehiculos = getVehiculos();
        Vehiculo vehiculo = getVehiculos().get(0);
        Optional<Vehiculo> vehiculoOptional = Optional.of(vehiculo);
        when(vehiculoRepository.findVehiculoBypatente(vehiculos.get(0).getPatente())).thenReturn(vehiculoOptional);

        // Act
        Optional<Vehiculo> resultado = vehiculoService.findVehiculoBypatente("AB1234");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isPresent());
    }

    @Test
    public void siInvocoFindVehiculoBypatenteYNoEstaElVehiculoDebeRetornarOptionalVacio(){
        // Arrange
        List<Vehiculo> vehiculos = getVehiculos();
        Optional<Vehiculo> vehiculoOptionalVacio = Optional.empty();
        when(vehiculoRepository.findVehiculoBypatente(vehiculos.get(0).getPatente())).thenReturn(vehiculoOptionalVacio);

        // Act
        Optional<Vehiculo> resultado = vehiculoService.findVehiculoBypatente("AB1234");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    @Test
    public void siInvocoDeleteVehiculoByIdYElVehiculoSeEliminaDebeRetornarVerdadero(){
        // Arrange
        Vehiculo vehiculo = getVehiculos().get(0);
        Optional<Vehiculo> vehiculoOptional = Optional.of(vehiculo);
        when(vehiculoRepository.findById(vehiculo.getId_vehiculo())).thenReturn(vehiculoOptional);

        // Act
        boolean resultado = vehiculoService.deleteVehiculoById(1);

        // Assert
        assertTrue(resultado);
    }

    @Test
    public void siInvocoDeleteVehiculoByIdYElVehiculoNoExisteDebeRetornarFalso(){
        // Arrange
        Vehiculo vehiculo = getVehiculos().get(0);
        Optional<Vehiculo> vehiculoOptionalVacio = Optional.empty();
        when(vehiculoRepository.findById(vehiculo.getId_vehiculo())).thenReturn(vehiculoOptionalVacio);

        // Act
        boolean resultado = vehiculoService.deleteVehiculoById(1);

        // Assert
        assertFalse(resultado);
    }



    private List<Vehiculo> getVehiculos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId_vehiculo(1);
        vehiculo.setPatente("AB1234");
        vehiculo.setId_tipoVehiculo(0);
        vehiculos.add(vehiculo);
        vehiculo = new Vehiculo();
        vehiculo.setId_vehiculo(2);
        vehiculo.setPatente("UB1234");
        vehiculo.setId_tipoVehiculo(0);
        vehiculos.add(vehiculo);
        return vehiculos;
    }

}
