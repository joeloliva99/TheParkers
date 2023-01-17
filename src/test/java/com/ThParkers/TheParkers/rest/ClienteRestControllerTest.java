package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.model.Cliente;
import com.ThParkers.TheParkers.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClienteRestControllerTest {
    @Mock
    private ClienteService clienteService;
    @InjectMocks
    private ClienteRestController clienteRestController;

    private MockMvc mockMvc;
    private JacksonTester<Cliente> jsonCliente;
    private JacksonTester<List<Cliente>> jsonClienteList;
    private JacksonTester<List<Integer>> jsonIntegerList;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(clienteRestController).build();
    }


    @Test
    public void siInvocoGetAllClientesYExisteClientesRetornaListaClientesYStatusOk() throws Exception {
        // Arrange
        Cliente cliente = getClientes().get(0);
        List<Cliente> clienteList = List.of(cliente);
        when(clienteService.findAllClientes()).thenReturn(clienteList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/clientes")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonClienteList.write(clienteList).getJson(),response.getContentAsString());
    }

    @Test
    public void siInvocoGetAllClientesYNoExisteClientesRetornaListaVaciaYStatusNotFound() throws Exception {
        // Arrange
        List<Cliente> clienteList = List.of();
        when(clienteService.findAllClientes()).thenReturn(clienteList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/clientes")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }

    @Test
    public void siInvocoNewClienteYSePuedeGrabarRetornarStatusCreated() throws Exception{
        // Arrange
        Cliente cliente = getClientes().get(0);
        when(clienteService.save(any(Cliente.class))).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/clientes")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonCliente.write(cliente).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
    }

    @Test
    public void siInvocoNewClienteYNoePuedeGrabarRetornarStatusBadRequest() throws Exception{
        // Arrange
        Cliente cliente = getClientes().get(0);
        when(clienteService.save(any(Cliente.class))).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/clientes")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonCliente.write(cliente).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    public void siInvocoGetClienteByRUTYExisteClienteConEseRutRetornaClienteYStatusOk() throws Exception{
        // Arrange
        Cliente cliente = getClientes().get(0);
        Optional<Cliente> clienteOptional = Optional.of(cliente);
        when(clienteService.findClienteByRUT(cliente.getRutCliente())).thenReturn(clienteOptional);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/clientes/"+cliente.getRutCliente())
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonCliente.write(cliente).getJson(),response.getContentAsString());
    }

    @Test
    public void siInvocoGetClienteByRUTYNoExisteClienteConEseRutRetornaStatusNotFoun() throws Exception{
        // Arrange
        Optional<Cliente> clienteOptional = Optional.empty();
        when(clienteService.findClienteByRUT("200000000")).thenReturn(clienteOptional);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/clientes/200000000")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }


    @Test
    public void siInvocoDeleteClienteByIDYSePuedeBorrarElClienteRetornaStatusOk() throws Exception{
        // Arrange
        Cliente cliente = getClientes().get(0);
        when(clienteService.deleteClienteById(cliente.getIdCliente())).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/clientes/"+cliente.getIdCliente())
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }


    @Test
    public void siInvocoDeleteClienteByIDYNoSePuedeBorrarElClienteRetornaStatusStatusNotFound() throws Exception{
        // Arrange
        when(clienteService.deleteClienteById(1)).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/clientes/1/")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
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
