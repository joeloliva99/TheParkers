package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.dummy.AtencionNueva;
import com.ThParkers.TheParkers.model.Atencion;
import com.ThParkers.TheParkers.service.AtencionService;
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
public class AtencionRestControllerTest {

    @Mock
    private AtencionService atencionService;
    @InjectMocks
    private AtencionRestController atencionRestController;

    private MockMvc mockMvc;
    private JacksonTester<Atencion> jsonAtencion;
    private JacksonTester<AtencionNueva> jsonAtencionNueva;
    private JacksonTester<List<Atencion>> jsonAtencionList;
    private JacksonTester<List<Integer>> jsonIntegerList;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(atencionRestController).build();
    }

    @Test
    public void siInvocoGetAllAtencionesYExistenAtencionesRetornaListaAtencionesYStatusOk() throws Exception {
        // Arrange
        Atencion atencion = getAtenciones().get(0);
        List<Atencion> atencionList = List.of(atencion);
        when(atencionService.findAllAtenciones()).thenReturn(atencionList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/atenciones/")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(jsonAtencionList.write(atencionList).getJson(),response.getContentAsString());
    }

    @Test
    public void siInvocoGetAllAtencionesYNoExistenAtencionesRetornaLNotFound() throws Exception {
        // Arrange
        List<Atencion> atencionList = List.of();
        when(atencionService.findAllAtenciones()).thenReturn(atencionList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/atenciones/")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }


    @Test
    public void siInvocoAddAtencionYSePuedeGrabarRetornarStatusCreated() throws Exception{
        // Arrange
        AtencionNueva atencionNueva = getAtencionNueva().get(0);
        when(atencionService.addAtencion(any(AtencionNueva.class))).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/atenciones/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonAtencionNueva.write(atencionNueva).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
    }


    @Test
    public void siInvocoAddAtencionYNoSePuedeGrabarRetornarStatusBadRequest() throws Exception{
        // Arrange
        AtencionNueva atencionNueva = getAtencionNueva().get(0);
        when(atencionService.addAtencion(any(AtencionNueva.class))).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/atenciones/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonAtencionNueva.write(atencionNueva).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }


    @Test
    public void siInvocoSaleAutoYExisteAtencionActivaRetornarStatusOK() throws Exception{
        // Arrange
        Atencion atencion = getAtenciones().get(0);
        when(atencionService.atencionFuera(atencion.getAtencionID())).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put("/atenciones/" + atencion.getAtencionID())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonAtencion.write(atencion).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }


    @Test
    public void siInvocoSaleAutoYNoExisteAtencionActivaRetornarStatusNotFound() throws Exception{
        // Arrange
        Atencion atencion = getAtenciones().get(0);
        when(atencionService.atencionFuera(atencion.getAtencionID())).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put("/atenciones/" + atencion.getAtencionID())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonAtencion.write(atencion).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
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

    private List<AtencionNueva> getAtencionNueva() {
        List<AtencionNueva> atencionNuevaList = new ArrayList<>();
        AtencionNueva atencionNueva = new AtencionNueva();
        atencionNueva.setRutCliente("1000099876");
        atencionNueva.setHoraEntrada("2023-01-16 13:31");
        atencionNueva.setPatente("AB1234");
        atencionNueva.setNronivel(1);
        atencionNueva.setNroEstacionamiento(1);
        atencionNueva.setHay_lavado(false);
        atencionNuevaList.add(atencionNueva);
        return atencionNuevaList;
    }
}
