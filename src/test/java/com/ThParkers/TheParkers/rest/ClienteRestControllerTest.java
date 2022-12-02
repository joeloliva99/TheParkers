package com.ThParkers.TheParkers.rest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

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

import com.TheParkers.TheParkers.controller.ClienteRestController;
import com.TheParkers.TheParkers.model.cliente;
import com.TheParkers.TheParkers.service.ClienteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ClienteRestControllerTest {
	@Mock
    private ClienteServiceImpl clienteService;
	
	@InjectMocks
    private ClienteRestController clienteController;
	
	private MockMvc mockMvc;
	private JacksonTester<cliente>jsonCliente;
	private JacksonTester<List<cliente>>jsonClienteList;
	
	@BeforeEach
    public void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }
	
	@Test
    public void siInvocoGetAllClientesYExistenClientesRetornaListaClientesYStatusOk() throws Exception{
		// Arrange
		cliente cliente = getCliente();
		List<cliente> clienteList = List.of(cliente);
		given(clienteService.buscarTodosLosCliente()).willReturn(clienteList);
		
		// Act
		MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/cliente")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		
		// Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonClienteList.write(clienteList).getJson(),response.getContentAsString());
	}
	
	@Test
    public void siInvocoGetAllClientesYNoExistenClientesoRetornaStatusNotFound() throws Exception{
		// Arrange
		List<cliente> clienteList = List.of();
		given(clienteService.buscarTodosLosCliente()).willReturn(clienteList);
		
		// Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/cliente")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		
		// Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());			
	}
	
	@Test
    public void siInvocoGetClienteByRUTYExisteClienteConEseRUTRetornaClienteYStatusOk() throws Exception{
		// Arrange
		cliente cliente = getCliente();
		Optional<cliente> clienteOptional = Optional.of(cliente);
		given(clienteService.findclienteByRUT(cliente.getRutCliente())).willReturn(clienteOptional);
		
		// Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/cliente/"+cliente.getRutCliente())
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        
     // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonCliente.write(cliente).getJson(),response.getContentAsString());		
	}
	
	@Test
    public void siInvocoGetClienteByRUTYNoExisteClienteConEseRUTRetornaStatusNotFound() throws Exception{
		// Arrange
		Optional<cliente> clienteOptional = Optional.empty();
		given(clienteService.findclienteByRUT("9195385K")).willReturn(clienteOptional);
		
		// Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/cliente/9195385K")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        
     // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());	
	}
	
	@Test
    public void siInvocoNewClienteYSePuedeGrabarRetornaStatusCreated() throws Exception{
        // Arrange
		cliente cliente = getCliente();
		given(clienteService.Guardar(any(cliente.class))).willReturn(true);
		
		// Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/cliente")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonCliente.write(cliente).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        
     // Assert
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());	
	}
	
	@Test
    public void siInvocoNewClienteYNoSePuedeGrabarRetornaStatusBadRequest() throws Exception{
        // Arrange
		cliente cliente = getCliente();
		given(clienteService.Guardar(any(cliente.class))).willReturn(false);
		
		// Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/cliente")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonCliente.write(cliente).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        
     // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
	}
	
	private cliente getCliente() {
		cliente cliente = new cliente();
        cliente.setId_cliente(1);
        cliente.setRutCliente("19127345K");
        cliente.setNombreCliente("Juan Mendez");
        cliente.setTelefonoCliente(12345678);
        cliente.setDireccionCliente("Av. Prueba 1");
        cliente.setCorreo("juan.mendez@supermail.com");
        return cliente;
	}

}
