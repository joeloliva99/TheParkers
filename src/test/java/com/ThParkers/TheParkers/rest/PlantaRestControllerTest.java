package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.dummy.PlantaTarifa;
import com.ThParkers.TheParkers.dummy.StatusPlanta;
import com.ThParkers.TheParkers.model.Planta;
import com.ThParkers.TheParkers.service.PlantaService;
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
public class PlantaRestControllerTest {
    @Mock
    private PlantaService plantaService;
    @InjectMocks
    private PlantaRestController plantaRestController;

    private MockMvc mockMvc;
    private JacksonTester<Planta> jsonPlanta;
    private JacksonTester<PlantaTarifa> jsonPlantaTarifa;
    private JacksonTester<StatusPlanta> jsonStatusPlanta;
    private JacksonTester<List<Planta>> jsonPlantaList;
    private JacksonTester<List<Integer>> jsonIntegerList;


    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(plantaRestController).build();
    }


    @Test
    public void siInvocoGetAllPlantasYExistenPlantasRetornaListaPlantasYStatusOk() throws Exception {
        // Arrange
        Planta planta = getPlantas().get(0);
        List<Planta> plantaList = List.of(planta);
        when(plantaService.findAllPlantas()).thenReturn(plantaList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/plantas")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonPlantaList.write(plantaList).getJson(),response.getContentAsString());
    }


    @Test
    public void siInvocoGetAllPlantasYNoExistenPlantasRetornaListaVaciaYStatusStatusNotFound() throws Exception {
        // Arrange
        List<Planta> plantaList = List.of();
        when(plantaService.findAllPlantas()).thenReturn(plantaList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/plantas")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }


    @Test
    public void siInvocoGetPlantaByIdYExistePlantaConEseIdRetornaPlantaYStatusOk() throws Exception{
        // Arrange
        Planta planta = getPlantas().get(0);
        Optional<Planta> optionalPlanta = Optional.of(planta);
        when(plantaService.findPlantaById(planta.getId_planta())).thenReturn(optionalPlanta);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/plantas/"+planta.getId_planta())
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonPlanta.write(planta).getJson(),response.getContentAsString());
    }


    @Test
    public void siInvocoGetPlantaByIdYNoExistePlantaConEseIdRetornaStatusNotFound() throws Exception{
        // Arrange
        Optional<Planta> optionalPlanta = Optional.empty();
        when(plantaService.findPlantaById(1)).thenReturn(optionalPlanta);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/plantas/1")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }



    @Test
    public void siInvocoAddPlantaYSePuedeGrabarRetornarStatusCreated() throws Exception {
        // Arrange
        Planta planta = getPlantas().get(0);
        when(plantaService.save(any(Planta.class))).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/plantas/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonPlanta.write(planta).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
    }

    @Test
    public void siInvocoAddPlantaYNoSePuedeGrabarRetornarBadRequest() throws Exception {
        // Arrange
        Planta planta = getPlantas().get(0);
        when(plantaService.save(any(Planta.class))).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/plantas/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonPlanta.write(planta).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }



    @Test
    public void siInvocoUpdatePlantaYSePuedeActualizarRetornarStatusOK() throws Exception{
        // Arrange
        Planta planta = getPlantas().get(0);
        when(plantaService.update(any(Planta.class))).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put("/plantas/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonPlanta.write(planta).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }


    @Test
    public void  siInvocoUpdatePlantaNoSePuedeActualizarRetornarBadRequest() throws Exception{
        // Arrange
        Planta planta = getPlantas().get(0);
        when(plantaService.update(any(Planta.class))).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put("/plantas/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonPlanta.write(planta).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }


    @Test
    public void siInvocoupdateTarifaYSePuedeActualizarTarifaRetornarStatusOK() throws Exception{
        // Arrange
        Planta planta = getPlantas().get(0);
        PlantaTarifa plantaTarifa = getPlantaTarifa().get(0);
        when(plantaService.updateTarifa(plantaTarifa.getTarifa_planta(), plantaTarifa.getId_planta())).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put("/plantas/tarifa")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonPlantaTarifa.write(plantaTarifa).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }


    @Test
    public void siInvocoupdateTarifaYNoSePuedeActualizarTarifaRetornarStatusOK() throws Exception{
        // Arrange
        Planta planta = getPlantas().get(0);
        PlantaTarifa plantaTarifa = getPlantaTarifa().get(0);
        when(plantaService.updateTarifa(plantaTarifa.getTarifa_planta(), plantaTarifa.getId_planta())).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put("/plantas/tarifa")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonPlantaTarifa.write(plantaTarifa).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    public void siInvocoGetStatusPlantaByIdYExistePlantaConEseIdRetornaPlantaYStatusOk() throws Exception{
        // Arrange
        Planta planta = getPlantas().get(0);
        StatusPlanta statusPlanta = getStatusPlanta().get(0);
        Optional<StatusPlanta> optionalStatusPlanta = Optional.of(statusPlanta);
        when(plantaService.returnestatusById(planta.getId_planta())).thenReturn(optionalStatusPlanta);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/plantas/"+ planta.getId_planta()+ "/status")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonStatusPlanta.write(statusPlanta).getJson(),response.getContentAsString());
    }


    @Test
    public void siInvocoGetStatusPlantaByIdYNoExistePlantaConEseIdRetornaStatusNotFound() throws Exception{
        // Arrange
        Optional<StatusPlanta> optionalStatusPlanta = Optional.empty();
        when(plantaService.returnestatusById(1)).thenReturn(optionalStatusPlanta);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/plantas/1/status")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }

    private List<Planta> getPlantas() {
        List<Planta> plantaList = new ArrayList<>();
        Planta planta = new Planta();
        planta.setId_planta(1);
        planta.setDireccion("Avenida Chillan");
        planta.setTarifa_planta(100);
        planta.setId_encargado(1);
        plantaList.add(planta);
        planta = new Planta();
        planta.setId_planta(2);
        planta.setDireccion("Calle chica");
        planta.setTarifa_planta(200);
        planta.setId_encargado(2);
        plantaList.add(planta);
        return plantaList;
    }


    private List<PlantaTarifa> getPlantaTarifa() {
        List<PlantaTarifa> plantaTarifaList = new ArrayList<>();
        PlantaTarifa plantaTarifa = new PlantaTarifa();
        plantaTarifa.setId_planta(1);
        plantaTarifa.setTarifa_planta(300);
        plantaTarifaList.add(plantaTarifa);
        return plantaTarifaList;
    }


    private List<StatusPlanta> getStatusPlanta() {
        List<StatusPlanta> statusPlantaList = new ArrayList<>();
        StatusPlanta statusPlanta = new StatusPlanta();
        statusPlanta.setIdPlanta(1);
        statusPlanta.setNroNiveles(2);
        statusPlanta.setTotalEstac(50);
        statusPlanta.setProporLleno("1/50");
        statusPlanta.setEstado("OK");
        statusPlantaList.add(statusPlanta);
        return statusPlantaList;
    }


}
