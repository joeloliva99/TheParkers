package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.model.Vehiculo;
import com.ThParkers.TheParkers.service.VehiculoService;
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
public class VehiculoRestControllerTest {
    @Mock
    private VehiculoService vehiculoService;
    @InjectMocks
    private VehiculoRestController vehiculoRestController;

    private MockMvc mockMvc;
    private JacksonTester<Vehiculo> jsonVehiculo;
    private JacksonTester<List<Vehiculo>> jsonVehiculoList;
    private JacksonTester<List<Integer>> jsonIntegerList;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(vehiculoRestController).build();
    }


    @Test
    public void siInvocoGetAllVehiculosYExistenVehiculosRetornaListaVehiculosYStatusOk() throws Exception {
        // Arrange
        Vehiculo vehiculo = getListaVehiculos().get(0);
        List<Vehiculo> vehiculoList = List.of(vehiculo);
        when(vehiculoService.findAllVehiculos()).thenReturn(vehiculoList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/vehiculos/")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonVehiculoList.write(vehiculoList).getJson(),response.getContentAsString());
    }


    @Test
    public void siInvocoGetAllVehiculosYNoExistenVehiculosRetornaListaVaciaYStatusStatusNotFound() throws Exception {
        // Arrange
        List<Vehiculo> vehiculoList = List.of();
        when(vehiculoService.findAllVehiculos()).thenReturn(vehiculoList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/vehiculos/")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }



    @Test
    public void siInvocoNewVehiculoYSePuedeGrabarRetornarStatusCreated() throws Exception{
        // Arrange
        Vehiculo vehiculo = getListaVehiculos().get(0);
        when(vehiculoService.save(any(Vehiculo.class))).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/vehiculos")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonVehiculo.write(vehiculo).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
    }


    @Test
    public void siInvocoNewVehiculoYNoSePuedeGrabarRetornarBadRequest() throws Exception{
        // Arrange
        Vehiculo vehiculo = getListaVehiculos().get(0);
        when(vehiculoService.save(any(Vehiculo.class))).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/vehiculos")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonVehiculo.write(vehiculo).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    public void siInvocoGetVehiculoByPatenteYExisteVehiculoConEsaPatenteRetornaVehiculoYStatusOk() throws Exception{
        // Arrange
        Vehiculo vehiculo = getListaVehiculos().get(0);
        Optional<Vehiculo> optionalVehiculo = Optional.of(vehiculo);
        when(vehiculoService.findVehiculoBypatente(vehiculo.getPatente())).thenReturn(optionalVehiculo);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/vehiculos/"+vehiculo.getPatente())
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonVehiculo.write(vehiculo).getJson(),response.getContentAsString());
    }

    @Test
    public void siInvocoGetVehiculoByPatenteYNoExisteVehiculoConEsaPatenteRetornaStatusNotFound() throws Exception{
        // Arrange
        Optional<Vehiculo> optionalVehiculo = Optional.empty();
        when(vehiculoService.findVehiculoBypatente("AB1234")).thenReturn(optionalVehiculo);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/vehiculos/AB1234")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }


    @Test
    public void siInvocoDeleteVehiculoByIDYSePuedeBorrarElVehiculoRetornaStatusOk() throws Exception{
        // Arrange
        Vehiculo vehiculo = getListaVehiculos().get(0);
        when(vehiculoService.deleteVehiculoById(vehiculo.getIdVehiculo())).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/vehiculos/"+vehiculo.getIdVehiculo())
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }


    @Test
    public void siInvocoDeleteVehiculoByIDYNoSePuedeBorrarElVehiculoRetornaStatusNotFound() throws Exception{
        // Arrange
        when(vehiculoService.deleteVehiculoById(1)).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/vehiculos/1")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }

    private List<Vehiculo> getListaVehiculos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(1);
        vehiculo.setPatente("AB1234");
        vehiculo.setId_tipoVehiculo(0);
        vehiculos.add(vehiculo);
        vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(2);
        vehiculo.setPatente("UB1234");
        vehiculo.setId_tipoVehiculo(0);
        vehiculos.add(vehiculo);
        return vehiculos;
    }
}
