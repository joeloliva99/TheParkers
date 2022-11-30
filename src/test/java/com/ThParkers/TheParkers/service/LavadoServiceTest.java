package com.ThParkers.TheParkers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.TheParkers.TheParkers.repository.RepositorioLavado;
import com.TheParkers.TheParkers.service.LavadoServiceImpl;
import com.TheParkers.TheParkers.model.Lavado;

@ExtendWith(MockitoExtension.class)
public class LavadoServiceTest {
	
	@Mock
    private RepositorioLavado repositorioLavado;
	
	@InjectMocks
    private LavadoServiceImpl lavadoService;
	
	@Test
    public void siInvocoBuscarTodosLosLavadosYExistenLavadosDebeRetornarListaDeLavados(){
		// Arrange
        List<Lavado>lavados = getLavados();
        when(repositorioLavado.findAll()).thenReturn(lavados);
        
        // Act
        List<Lavado> resultado = lavadoService.buscarTodosLosLavados();
        
     // Assert
        assertNotNull(resultado);
        assertEquals(lavados.size(),resultado.size());
        assertEquals(lavados.get(0),resultado.get(0));
		
	}
	
	@Test
    public void siInvocoBuscarTodosLosLavadosYNoExistenLavadosDebeRetornarListaVacia(){
		// Arrange
        when(repositorioLavado.findAll()).thenReturn(new ArrayList<>());
        
        // Act
        List<Lavado> resultado = lavadoService.buscarTodosLosLavados();
        
        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
		
	}
	
	@Test
	public void siInvocoBuscarLavadoPorIdYExisteLavadoDebeRetornarElLavado() {
		// Arrange
        Lavado lavado = getLavadoPorId(1);
        Optional<Lavado> lavadoOptional = Optional.of(lavado);
        when(repositorioLavado.findById(lavado.getId_lavado()))
        		.thenReturn(lavadoOptional);

        // Act
        Lavado resultado = lavadoService.BuscarLavadoPorId(lavado.getId_lavado());

        // Assert
        assertNotNull(resultado);
        
	}
	
	@Test
	public void siInvocoBuscarLavadoPorIdExisteLavadoNoDebeRetornarNada() {
		// Arrange
        Lavado lavado = new Lavado();
        Optional<Lavado> lavadoOptional = Optional.of(lavado);
        when(repositorioLavado.findById(lavado.getId_lavado()))
        		.thenReturn(lavadoOptional);

        // Act
        Lavado resultado = lavadoService.BuscarLavadoPorId(lavado.getId_lavado());

        // Assert
        assertNotNull(resultado);
        
	}
	
	
	private List<Lavado> getLavados(){
		List<Lavado>lavados = new ArrayList<>();
		Lavado lavado = new Lavado();
		lavado.setId_lavado(1);
		lavado.setRutEmpleado("19127345K");
		lavados.add(lavado);
		
		lavado = new Lavado();
		lavado.setId_lavado(2);
		lavado.setRutEmpleado("91953851");
		lavados.add(lavado);
		
		return lavados;
	}
	
	private Lavado getLavadoPorId(int id) {
		Lavado lavado = new Lavado();
		lavado.setId_lavado(1);
		lavado.setRutEmpleado("19127345K");
		
		if(lavado.getId_lavado()==id) {
			return lavado;	
		}else {
			return null;
		}
	}

}
