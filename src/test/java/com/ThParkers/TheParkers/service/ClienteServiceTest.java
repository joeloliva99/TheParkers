package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.model.Cliente;
import com.ThParkers.TheParkers.model.Empleado;
import com.ThParkers.TheParkers.model.TipoVehiculo;
import com.ThParkers.TheParkers.repository.ClienteRepository;
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
public class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private ClienteService clienteService;

    @Test
    public void siInvocoFindAllClientesYExistenClientesDebeRetornarListaDeClientes(){
        // Arrange
        List<Cliente> clienteList = getClientes();
        when(clienteRepository.findAll()).thenReturn(clienteList);

        // Act
        List<Cliente> resultado = clienteService.findAllClientes();

        // Assert
        assertNotNull(resultado);
        assertEquals(clienteList.size(),resultado.size());
        assertEquals(clienteList.get(0),resultado.get(0));
    }


    @Test
    public void siInvocoFindAllClientesYNoExistenClientesDebeRetornarListaVacia(){
        // Arrange
        when(clienteRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Cliente> resultado = clienteService.findAllClientes();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    @Test
    public void siInvocoSaveYSePuedeAlmacenarElObjetoDebeRetornarVerdadero(){
        // Arrange
        Cliente cliente = getClientes().get(0);
        Optional<Cliente> clienteOptional = Optional.of(cliente);
        when(clienteRepository.findClienteByRutCliente(cliente.getRutCliente())).thenReturn(clienteOptional);

        // Act
        boolean resultado = clienteService.save(cliente);

        // Assert
        assertTrue(resultado);
    }


    @Test
    public void siInvocoSaveYNoSePuedeAlmacenarElObjetoDebeRetornarFalso(){
        // Arrange
        Cliente cliente = getClientes().get(0);
        Optional<Cliente> clienteOptionalVacio = Optional.empty();
        when(clienteRepository.findClienteByRutCliente(cliente.getRutCliente())).thenReturn(clienteOptionalVacio);

        // Act
        boolean resultado = clienteService.save(cliente);

        // Assert
        assertFalse(resultado);
    }


    @Test
    public void siInvocoFindClienteByRUTDebeRetornarOptionalConCliente(){
        // Arrange
        Cliente cliente = getClientes().get(0);
        Optional<Cliente> clienteOptional = Optional.of(cliente);
        when(clienteRepository.findClienteByRutCliente(cliente.getRutCliente())).thenReturn(clienteOptional);

        // Act
        Optional<Cliente> resultado = clienteService.findClienteByRUT("100000000");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isPresent());
    }

    @Test
    public void siInvocoFindClienteByRUTYNoEstaElClienteDebeRetornarOptionalVacio(){
        // Arrange
        List<Cliente> clientes = getClientes();
        Optional<Cliente> clienteOptionalVacio = Optional.empty();
        when(clienteRepository.findClienteByRutCliente(clientes.get(0).getRutCliente())).thenReturn(clienteOptionalVacio);

        // Act
        Optional<Cliente> resultado = clienteService.findClienteByRUT("100000000");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    @Test
    public void siInvocoDeleteClienteByIdYElClienteSeEliminaDebeRetornarVerdadero(){
        // Arrange
        Cliente cliente = getClientes().get(0);
        Optional<Cliente> clienteOptional = Optional.of(cliente);
        when(clienteRepository.findById(cliente.getIdCliente())).thenReturn(clienteOptional);

        // Act
        boolean resultado = clienteService.deleteClienteById(1);

        // Assert
        assertTrue(resultado);
    }

    @Test
    public void siInvocoDeleteClienteByIdYIdYElClienteNoExisteDebeRetornarFalso(){
        // Arrange
        Cliente cliente = getClientes().get(0);
        Optional<Cliente> clienteOptionalVacio = Optional.empty();
        when(clienteRepository.findById(cliente.getIdCliente())).thenReturn(clienteOptionalVacio);

        // Act
        boolean resultado = clienteService.deleteClienteById(1);

        // Assert
        assertFalse(resultado);
    }




    private List<Cliente> getClientes() {
        List<Cliente> clienteList = new ArrayList<>();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setRutCliente("100000000");
        cliente.setNombreCliente("Alberto Albertini");
        cliente.setTelefonoCliente(912345678);
        cliente.setDireccionCliente("Calle Los Almendros");
        cliente.setCorreo("aa@gmail.com");
        clienteList.add(cliente);
        cliente = new Cliente();
        cliente.setIdCliente(2);
        cliente.setRutCliente("200000000");
        cliente.setNombreCliente("Roberto Robertino");
        cliente.setTelefonoCliente(987654321);
        cliente.setDireccionCliente("Calle Los Nogales");
        cliente.setCorreo("rr@gmail.com");
        clienteList.add(cliente);
        return clienteList;
    }

}
