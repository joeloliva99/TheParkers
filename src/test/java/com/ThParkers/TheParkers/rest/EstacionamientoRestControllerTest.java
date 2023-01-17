package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.model.Estacionamiento;
import com.ThParkers.TheParkers.service.EstacionamientoService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class EstacionamientoRestControllerTest {
    @Mock
    private EstacionamientoService estacionamientoService;
    @InjectMocks
    private EstacionamientoRestController estacionamientoRestController;


    private MockMvc mockMvc;
    private JacksonTester<Estacionamiento> jsonEstacionamiento;
    private JacksonTester<List<Estacionamiento>> jsonEstacionamientoList;
    private JacksonTester<List<Integer>> jsonIntegerList;


    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(estacionamientoRestController).build();
    }


    @Test
    public void siInvocoGetAllEstacionamientosYExistenEstacionamientosRetornaListaEstacionamientosYStatusOk() throws Exception {
        // Arrange
        Estacionamiento estacionamiento = getEstacionamientos().get(0);
        List<Estacionamiento> estacionamientoList = List.of(estacionamiento);
        when(estacionamientoService.findAllEstacionamientos()).thenReturn(estacionamientoList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/estacionamientos")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonEstacionamientoList.write(estacionamientoList).getJson(),response.getContentAsString());
    }


    @Test
    public void siInvocoGetAllEstacionamientosYNoExistenEstacionamientosRetornaListaVaciaYStatusStatusNotFound() throws Exception {
        // Arrange
        List<Estacionamiento> estacionamientoList = List.of();
        when(estacionamientoService.findAllEstacionamientos()).thenReturn(estacionamientoList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/estacionamientos")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }


    @Test
    public void siInvocoGetEstacionamientosDisponiblesPorIdYExistenEstacionamientosDisponiblesRetornaListaEstacionamientosYStatusOk() throws Exception {
        // Arrange
        Estacionamiento estacionamiento = getEstacionamientos().get(0);
        List<Estacionamiento> estacionamientoList = List.of(estacionamiento);
        when(estacionamientoService.estacionamientosDisponiblesPorNivel(1)).thenReturn(estacionamientoList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/estacionamientos/nivel/1")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonEstacionamientoList.write(estacionamientoList).getJson(),response.getContentAsString());
    }


    @Test
    public void siInvocoGetEstacionamientosDisponiblesPorIdYNoHayEstacionamientosDisponiblesRetornaListaVacíaYStatusNotFound() throws Exception {
        // Arrange
        List<Estacionamiento> estacionamientoList = List.of();
        when(estacionamientoService.estacionamientosDisponiblesPorNivel(1)).thenReturn(estacionamientoList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/estacionamientos/nivel/1")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }


    private List<Estacionamiento> getEstacionamientos () {
        List<Estacionamiento> estacionamientoList = new ArrayList<>();
        Estacionamiento estacionamiento = new Estacionamiento();
        estacionamiento.setId_estacionamiento(1);
        estacionamiento.setNro_estacionamiento(1);
        estacionamiento.setId_nivel(1);
        estacionamiento.setDisponible(true);
        estacionamientoList.add(estacionamiento);
        estacionamiento = new Estacionamiento();
        estacionamiento.setId_estacionamiento(2);
        estacionamiento.setNro_estacionamiento(2);
        estacionamiento.setId_nivel(1);
        estacionamiento.setDisponible(true);
        estacionamientoList.add(estacionamiento);
        return estacionamientoList;
    }
}
