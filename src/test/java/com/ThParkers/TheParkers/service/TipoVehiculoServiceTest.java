package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.model.TipoVehiculo;
import com.ThParkers.TheParkers.repository.TipoVehiculoRepository;
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
public class TipoVehiculoServiceTest {
    @Mock
    private TipoVehiculoRepository tipoVehiculoRepository;
    @InjectMocks
    private TipoVehiculoService tipoVehiculoService;


    @Test
    public void siInvocoDevolverTarifaYTipoVehiculoExisteDebeRetornarOLaTarifa(){
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        Optional<TipoVehiculo> tipoVehiculoOptional = Optional.of(tipoVehiculo);
        when(tipoVehiculoRepository.findById(tipoVehiculo.getId_tipoVehiculo())).thenReturn(tipoVehiculoOptional);

        // Act
        int resultado = tipoVehiculoService.devolverTarifa(tipoVehiculo.getId_tipoVehiculo());

        // Assert
        assertEquals(resultado, tipoVehiculo.getTarifa());
    }


    @Test
    public void siInvocoFindAllTiposVehiculosYExistenTiposVehiculosDebeRetornarListaDeTiposVehiculos(){
        // Arrange
        List<TipoVehiculo> tipoVehiculos = getTiposVehiculos();
        when(tipoVehiculoRepository.findAll()).thenReturn(tipoVehiculos);

        // Act
        List<TipoVehiculo> resultado = tipoVehiculoService.findAllTiposVehiculos();

        // Assert
        assertNotNull(resultado);
        assertEquals(tipoVehiculos.size(),resultado.size());
        assertEquals(tipoVehiculos.get(0),resultado.get(0));
    }

    @Test
    public void siInvocoFindAllTiposVehiculosYNoExistenTiposVehiculosDebeRetornarListaVacia(){
        // Arrange
        when(tipoVehiculoRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<TipoVehiculo> resultado = tipoVehiculoService.findAllTiposVehiculos();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }



    @Test
    public void siInvocoSaveYSePuedeAlmacenarElObjetoDebeRetornarVerdadero(){
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        Optional<TipoVehiculo> tipoVehiculoOptional = Optional.of(tipoVehiculo);
        when(tipoVehiculoRepository.findTipoVehiculoByNTipoVehiculo(tipoVehiculo.getNTipoVehiculo()))
                .thenReturn(tipoVehiculoOptional);

        // Act
        boolean resultado = tipoVehiculoService.save(tipoVehiculo);

        // Assert
        assertTrue(resultado);
    }


    @Test
    public void siInvocoSaveYNoSePuedeAlmacenarElObjetoDebeRetornarFalso(){
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        Optional<TipoVehiculo> tipoVehiculoOptionalVacio = Optional.empty();
        when(tipoVehiculoRepository.findTipoVehiculoByNTipoVehiculo(tipoVehiculo.getNTipoVehiculo()))
                .thenReturn(tipoVehiculoOptionalVacio);

        // Act
        boolean resultado = tipoVehiculoService.save(tipoVehiculo);

        // Assert
        assertFalse(resultado);
    }

    @Test
    public void siInvocoDeleteTipoVehiculoByIdYElTipoVehiculoSeEliminaDebeRetornarVerdadero(){
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        Optional<TipoVehiculo> tipoVehiculoOptional = Optional.of(tipoVehiculo);
        when(tipoVehiculoRepository.findById(tipoVehiculo.getId_tipoVehiculo())).thenReturn(tipoVehiculoOptional);

        // Act
        boolean resultado = tipoVehiculoService.deleteTipoVehiculoById(tipoVehiculo.getId_tipoVehiculo());

        // Assert
        assertTrue(resultado);
    }

    @Test
    public void iInvocoDeleteTipoVehiculoByIdYElTipoVehiculoNoExisteDebeRetornarFalso(){
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        Optional<TipoVehiculo> tipoVehiculoOptionalVacio = Optional.empty();
        when(tipoVehiculoRepository.findById(tipoVehiculo.getId_tipoVehiculo())).thenReturn(tipoVehiculoOptionalVacio);

        // Act
        boolean resultado = tipoVehiculoService.deleteTipoVehiculoById(tipoVehiculo.getId_tipoVehiculo());

        // Assert
        assertFalse(resultado);
    }



    @Test
    public void siInvocoFindTipoVehiculoByIdDebeRetornarOptionalConTipoVehiculo(){
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        Optional<TipoVehiculo> tipoVehiculoOptional = Optional.of(tipoVehiculo);
        when(tipoVehiculoRepository.findById(tipoVehiculo.getId_tipoVehiculo())).thenReturn(tipoVehiculoOptional);

        // Act
        Optional<TipoVehiculo> resultado = tipoVehiculoService.findTipoVehiculoById(tipoVehiculo.getId_tipoVehiculo());

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isPresent());
    }

    @Test
    public void siInvocoFindTipoVehiculoByIdYNoEstaElTipoVehiculoDebeRetornarOptionalVacio(){
        // Arrange
        List<TipoVehiculo> tipoVehiculos = getTiposVehiculos();
        Optional<TipoVehiculo> tipoVehiculoOptionalVacio = Optional.empty();
        when(tipoVehiculoRepository.findById(tipoVehiculos.get(0).getId_tipoVehiculo())).thenReturn(tipoVehiculoOptionalVacio);

        // Act
        Optional<TipoVehiculo> resultado = tipoVehiculoService.findTipoVehiculoById(tipoVehiculos.get(0).getId_tipoVehiculo());

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void siInvocoUpdateYSePuedeActualizarDebeRetornarVerdadero(){
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        Optional<TipoVehiculo> tipoVehiculoOptional = Optional.of(tipoVehiculo);
        when(tipoVehiculoRepository.findById(tipoVehiculo.getId_tipoVehiculo()))
                .thenReturn(tipoVehiculoOptional);

        // Act
        boolean resultado = tipoVehiculoService.update(tipoVehiculo);

        // Assert
        assertTrue(resultado);
    }

    @Test
    public void siInvocoUpdateYNoSePuedeActualizarDebeRetornarFalse(){
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        Optional<TipoVehiculo> tipoVehiculoOptionalVacio = Optional.empty();
        when(tipoVehiculoRepository.findById(tipoVehiculo.getId_tipoVehiculo()))
                .thenReturn(tipoVehiculoOptionalVacio);

        // Act
        boolean resultado = tipoVehiculoService.update(tipoVehiculo);

        // Assert
        assertFalse(resultado);
    }


    private List<TipoVehiculo> getTiposVehiculos() {
        List<TipoVehiculo> tipoVehiculos = new ArrayList<>();
        TipoVehiculo tipoVehiculo = new TipoVehiculo();
        tipoVehiculo.setId_tipoVehiculo(1);
        tipoVehiculo.setNTipoVehiculo("Sedán");
        tipoVehiculo.setTarifa(100);
        tipoVehiculos.add(tipoVehiculo);
        tipoVehiculo = new TipoVehiculo();
        tipoVehiculo.setId_tipoVehiculo(2);
        tipoVehiculo.setNTipoVehiculo("Camioneta");
        tipoVehiculo.setTarifa(200);
        tipoVehiculos.add(tipoVehiculo);
        return tipoVehiculos;
    }
}

