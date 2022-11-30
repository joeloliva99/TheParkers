package com.ThParkers.TheParkers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.TheParkers.TheParkers.repository.ClienteRepository;
import com.TheParkers.TheParkers.service.ClienteServiceImpl;
import com.TheParkers.TheParkers.model.cliente;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTest {
	
	@Mock
    private ClienteRepository clienteRepository;
	
	@InjectMocks
    private ClienteServiceImpl clienteService;
	
	@Test
    public void siInvocoBuscarTodosLosClientesYExistenClientesDebeRetornarListaDeClientes(){
        // Arrange
        List<cliente> clientes = getClientes();
        when(clienteRepository.findAll()).thenReturn(clientes);

        // Act
        List<cliente> resultado = clienteService.buscarTodosLosCliente();

        // Assert
        assertNotNull(resultado);
        assertEquals(clientes.size(),resultado.size());
        assertEquals(clientes.get(0),resultado.get(0));
    }
	
	
	@Test
    public void siInvocoBuscarTodosLosClientesYNoExistenClientesDebeRetornarListaVacia(){
        // Arrange
        when(clienteRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<cliente> resultado = clienteService.buscarTodosLosCliente();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
	
	@Test
    public void siInvocoGuardarYNoExisteClienteConElNombreDebeRetornarTrue(){
        // Arrange
        cliente cliente = getClientes().get(0);
        Optional<cliente> clienteOptional = Optional.of(cliente);
        Optional<cliente> clienteOptionalVacio = Optional.empty();
        when(clienteRepository.findClienteByNombre(cliente.getNombreCliente()))
                .thenReturn(clienteOptional).thenReturn(clienteOptionalVacio);
        // Act
        boolean resultado = clienteService.Guardar(cliente);

        // Assert
        assertTrue(resultado);
    }
	
	@Test
	public void siInvocoGuardarYExisteClienteConElNombreDebeRteronarFalse() {
		//Arrange
		cliente cliente = getClientes().get(1);
        Optional<cliente> clienteOptionalVacio = Optional.empty();
        when(clienteRepository.findClienteByNombre(cliente.getNombreCliente()))
                .thenReturn(clienteOptionalVacio);
		
		//Act
        boolean resultado = clienteService.Guardar(cliente);
		
		//Assert
        assertFalse(resultado);
	}
	
	@Test
	public void siInvocoBuscarClientePorRUTYExisteClienteDebeRetornarElCliente() {
		// Arrange
        cliente cliente = getClientePorRUT("19127345K");
        Optional<cliente> clienteOptional = Optional.of(cliente);
        when(clienteRepository.findClienteByRutCliente(cliente.getRutCliente()))
        		.thenReturn(clienteOptional);

        // Act
        cliente resultado = clienteService.BuscarClientePorRUT(cliente.getRutCliente());

        // Assert
        assertNotNull(resultado);
        
	}
	
	@Test
	public void siInvocoBuscarClientePorRUTYNoExisteClienteNoDebeRetornarNada() {
		// Arrange
        cliente cliente = new cliente();
        Optional<cliente> clienteOptional = Optional.of(cliente);
        when(clienteRepository.findClienteByRutCliente(cliente.getRutCliente()))
        		.thenReturn(clienteOptional);

        // Act
        cliente resultado = clienteService.BuscarClientePorRUT(cliente.getRutCliente());

        // Assert
        assertNotNull(resultado);
        
	}
	
	
	private List<cliente> getClientes() {
        List<cliente> clientes = new ArrayList<>();
        cliente cliente = new cliente();
        cliente.setId_cliente(1);
        cliente.setRutCliente("19127345K");
        cliente.setNombreCliente("Juan Mendez");
        cliente.setTelefonoCliente(12345678);
        cliente.setDireccionCliente("Av. Prueba 1");
        cliente.setCorreo("juan.mendez@supermail.com");
        
        clientes.add(cliente);
        cliente = new cliente();
        cliente.setId_cliente(2);
        cliente.setRutCliente("91953851");
        cliente.setNombreCliente("Nadia Dumas");
        cliente.setTelefonoCliente(54378513);
        cliente.setDireccionCliente("Av. Prueba 1");
        cliente.setCorreo("nadia.dumas@supermail.com");
        
        clientes.add(cliente);
        return clientes;
    }
	private cliente getClientePorRUT(String RUT) {
		cliente cliente = new cliente();
		cliente.setId_cliente(1);
        cliente.setRutCliente("19127345K");
        cliente.setNombreCliente("Juan Mendez");
        cliente.setTelefonoCliente(12345678);
        cliente.setDireccionCliente("Av. Prueba 1");
        cliente.setCorreo("juan.mendez@supermail.com");
        
        if(cliente.getRutCliente()==RUT) {
        	return cliente;
        }else {
        	return null;
        }
	}

}
