package com.ThParkers.TheParkers.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.TheParkers.TheParkers.controller.LavadoController;
import com.TheParkers.TheParkers.model.Lavado;
import com.TheParkers.TheParkers.service.LavadoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class LavadoControllerTest {
	
	@Mock
    private LavadoServiceImpl lavadoService;
	
	@InjectMocks
    private LavadoController lavadoController;
	
	private MockMvc mockMvc;
	private JacksonTester<Lavado>jsonLavado;
	private JacksonTester<List<Lavado>>jsonLavadoList;
	
	@BeforeEach
    public void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(lavadoController).build();
    }
	
	@Test
    public void siInvocoGetAllLavadosYExistenLavadosRetornaListaLavadosYStatusOk() throws Exception{
		// Arrange
		Lavado lavado = getLavado();
		List<Lavado> lavadoList = List.of(lavado);
		given(lavadoService.buscarTodosLosLavados()).willReturn(lavadoList);
		// Act
		MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/lavado")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		// Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonLavadoList.write(lavadoList).getJson(),response.getContentAsString());
	}
	
	@Test
    public void siInvocoGetAllLavadosYNoExistenLavadosRetornaStatusNotFound() throws Exception{
		// Arrange
		List<Lavado> lavadoList = List.of();
		given(lavadoService.buscarTodosLosLavados()).willReturn(lavadoList);
		
		// Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/lavado")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		
		// Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());			
	}
	
	@Test
    public void siInvocoGetLavadoByIdYExisteLavadoConEseIdRetornaLavadoYStatusOk() throws Exception{
        // Arrange
        Lavado lavado = getLavado();
        Optional<Lavado>lavadoOptional = Optional.of(lavado);
        given(lavadoService.findLavadoById(lavado.getId_lavado())).willReturn(lavadoOptional);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/lavado/"+lavado.getId_lavado())
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonLavado.write(lavado).getJson(),response.getContentAsString());
    }
	
	@Test
    public void siInvocoGetLavadoByIdYNoExisteLavadoConEseIdRetornaStatusNotFound() throws Exception{
		// Arrange
		Optional<Lavado>lavadoOptional= Optional.empty();
		given(lavadoService.findLavadoById(2)).willReturn(lavadoOptional);
		
		// Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/lavado/2")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }
	
	
	private Lavado getLavado() {
		Lavado lavado = new Lavado();
		lavado.setId_lavado(1);
		lavado.setRutEmpleado("19127345K");
		return lavado;
	}

}
