package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.model.Atencion;
import com.ThParkers.TheParkers.model.Boleta;
import com.ThParkers.TheParkers.model.TipoVehiculo;
import com.ThParkers.TheParkers.service.BoletaService;
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
public class BoletaRestControllerTest {

    @Mock
    private BoletaService boletaService;
    @InjectMocks
    private BoletaRestController boletaRestController;

    private MockMvc mockMvc;
    private JacksonTester<Boleta> jsonBoleta;
    private JacksonTester<List<Boleta>> jsonBoletaList;
    private JacksonTester<List<Integer>> jsonIntegerList;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(boletaRestController).build();
    }


    @Test
    public void siInvocoGetBoletaByIdYAtencionExisteBoletaConEseIdRetornaBoletaYStatusOk() throws Exception{
        // Arrange
        Boleta boleta = getBoletas().get(0);
        Atencion atencion = getAtenciones().get(0);
        Optional<Boleta> optionalBoleta = Optional.of(boleta);
        when(boletaService.findBoletaByAtencion(atencion.getAtencionID())).thenReturn(optionalBoleta);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/boletas/"+atencion.getAtencionID())
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonBoleta.write(boleta).getJson(),response.getContentAsString());
    }


    @Test
    public void siInvocoGetBoletaByIdYAtencionYNoExisteBoletaConEseIdRetornaStatusNotFound() throws Exception{
        // Arrange
        Optional<Boleta> optionalBoleta = Optional.empty();
        when(boletaService.findBoletaByAtencion(1)).thenReturn(optionalBoleta);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/boletas/1/")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }


    private List<Boleta> getBoletas() {
        List<Boleta> boletaArrayList = new ArrayList<>();
        Boleta boleta = new Boleta();
        boleta.setId_boleta(1);
        boleta.setAtencionID(1);
        boleta.setSubtotal(1000);
        boleta.setDescuento(100);
        boleta.setTotal(900);
        boletaArrayList.add(boleta);
        boleta = new Boleta();
        boleta.setId_boleta(2);
        boleta.setAtencionID(2);
        boleta.setSubtotal(5000);
        boleta.setDescuento(100);
        boleta.setTotal(4900);
        boletaArrayList.add(boleta);
        return boletaArrayList;
    }

    private List<Atencion> getAtenciones() {
        List<Atencion> atencionList = new ArrayList<>();
        Atencion atencion = new Atencion();
        atencion.setAtencionID(1);
        atencion.setIdCliente(1);
        atencion.setHoraEntrada("2023-01-16 13:31");
        atencion.setHoraSalida("2023-01-16 16:31");
        atencion.setId_vehiculo(1);
        atencion.setId_empleado(1);
        atencion.setId_estacionamiento(1);
        atencion.setHay_lavado(false);
        atencion.setId_lavado(null);
        atencionList.add(atencion);
        atencion = new Atencion();
        atencion.setAtencionID(2);
        atencion.setIdCliente(1);
        atencion.setHoraEntrada("2023-01-17 13:31");
        atencion.setHoraSalida("2023-01-17 16:31");
        atencion.setId_vehiculo(1);
        atencion.setId_empleado(1);
        atencion.setId_estacionamiento(1);
        atencion.setHay_lavado(false);
        atencion.setId_lavado(null);
        atencionList.add(atencion);
        return atencionList;
    }
}
