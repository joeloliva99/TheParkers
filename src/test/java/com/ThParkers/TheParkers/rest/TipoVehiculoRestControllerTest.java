package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.model.TipoVehiculo;
import com.ThParkers.TheParkers.service.TipoVehiculoService;
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
public class TipoVehiculoRestControllerTest {
    @Mock
    private TipoVehiculoService tipoVehiculoService;
    @InjectMocks
    private TipoVehiculoRestController tipoVehiculoRestController;

    private MockMvc mockMvc;
    private JacksonTester<TipoVehiculo> jsonTipoVehiculo;
    private JacksonTester<List<TipoVehiculo>> jsonTipoVehiculoList;
    private JacksonTester<List<Integer>> jsonIntegerList;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(tipoVehiculoRestController).build();
    }

    @Test
    public void siInvocoGetAllTiposVehiculosYExistenTiposVehiculosRetornaListaTiposVehiculosYStatusOk() throws Exception {
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        List<TipoVehiculo> tipoVehiculoList = List.of(tipoVehiculo);
        when(tipoVehiculoService.findAllTiposVehiculos()).thenReturn(tipoVehiculoList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/tipovehiculos/")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonTipoVehiculoList.write(tipoVehiculoList).getJson(),response.getContentAsString());
    }


    @Test
    public void siInvocoGetAllTiposVehiculosYNoExistenTiposVehiculosRetornaListaVaciaYStatusStatusNotFound() throws Exception {
        // Arrange
        List<TipoVehiculo> tipoVehiculoList = List.of();
        when(tipoVehiculoService.findAllTiposVehiculos()).thenReturn(tipoVehiculoList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/tipovehiculos/")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }



    @Test
    public void siInvocoNewTipoVehiculoYSePuedeGrabarRetornarStatusCreated() throws Exception{
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        when(tipoVehiculoService.save(any(TipoVehiculo.class))).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/tipovehiculos/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonTipoVehiculo.write(tipoVehiculo).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
    }


    @Test
    public void siInvocoNewTipoVehiculoYNoSePuedeGrabarRetornarBadRequest() throws Exception{
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        when(tipoVehiculoService.save(any(TipoVehiculo.class))).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/tipovehiculos/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonTipoVehiculo.write(tipoVehiculo).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }



    @Test
    public void siInvocoGetTipoVehiculoByIDYExisteTipoVehiculoConEseIdRetornaTipoVehiculoYStatusOk() throws Exception{
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        Optional<TipoVehiculo> optionalTipoVehiculo = Optional.of(tipoVehiculo);
        when(tipoVehiculoService.findTipoVehiculoById(tipoVehiculo.getId_tipoVehiculo())).thenReturn(optionalTipoVehiculo);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/tipovehiculos/"+tipoVehiculo.getId_tipoVehiculo())
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonTipoVehiculo.write(tipoVehiculo).getJson(),response.getContentAsString());
    }

    @Test
    public void siInvocoGetTipoVehiculoByIDYNoExisteTipoVehiculoConEseIdRetornaStatusNotFound() throws Exception{
        // Arrange
        Optional<TipoVehiculo> optionalTipoVehiculo = Optional.empty();
        when(tipoVehiculoService.findTipoVehiculoById(1)).thenReturn(optionalTipoVehiculo);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/tipovehiculos/1")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }


    @Test
    public void siInvocoDeleteTipoVehiculoByIDYSePuedeBorrarElTipoVehiculoRetornaStatusOk() throws Exception{
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        when(tipoVehiculoService.deleteTipoVehiculoById(tipoVehiculo.getId_tipoVehiculo())).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/tipovehiculos/"+tipoVehiculo.getId_tipoVehiculo())
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }


    @Test
    public void siInvocoDeleteTipoVehiculoByIDYNoSePuedeBorrarElTipoVehiculoRetornaStatusNotFound() throws Exception{
        // Arrange
        when(tipoVehiculoService.deleteTipoVehiculoById(1)).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/tipovehiculos/1")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }

    @Test
    public void siInvocoUpdateTipoVehiculoYSePuedeActualizarRetornarStatusOK() throws Exception{
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        when(tipoVehiculoService.update(any(TipoVehiculo.class))).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put("/tipovehiculos/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonTipoVehiculo.write(tipoVehiculo).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    @Test
    public void  siInvocoUpdateTipoVehiculoYNoSePuedeActualizarRetornarBadRequest() throws Exception{
        // Arrange
        TipoVehiculo tipoVehiculo = getTiposVehiculos().get(0);
        when(tipoVehiculoService.update(any(TipoVehiculo.class))).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put("/tipovehiculos/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonTipoVehiculo.write(tipoVehiculo).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }


    private List<TipoVehiculo> getTiposVehiculos() {
        List<TipoVehiculo> tipoVehiculos = new ArrayList<>();
        TipoVehiculo tipoVehiculo = new TipoVehiculo();
        tipoVehiculo.setId_tipoVehiculo(1);
        tipoVehiculo.setNTipoVehiculo("Sedan");
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

