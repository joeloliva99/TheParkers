package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.model.Empleado;
import com.ThParkers.TheParkers.service.EmpleadoService;
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
public class EmpleadoRestControllerTest {
    @Mock
    private EmpleadoService empleadoService;
    @InjectMocks
    private EmpleadoRestController empleadoRestController;

    private MockMvc mockMvc;
    private JacksonTester<Empleado> jsonEmpleado;
    private JacksonTester<List<Empleado>> jsonEmpleadoList;
    private JacksonTester<List<Integer>> jsonIntegerList;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(empleadoRestController).build();
    }


    @Test
    public void siInvocoGetAllEmpleadosYExistenEmpleadoRetornaListaEmpleadoYStatusOk() throws Exception {
        // Arrange
        Empleado empleado = getEmplados().get(0);
        List<Empleado> empleadoList = List.of(empleado);
        when(empleadoService.findAllEmpleados()).thenReturn(empleadoList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/empleados/all/")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jsonEmpleadoList.write(empleadoList).getJson(), response.getContentAsString());
    }

    @Test
    public void siInvocoGetAllEmpleadosYYNoExistenEmpleadosRetornaListaVaciaYStatusStatusNotFound() throws Exception {
        // Arrange
        List<Empleado> empleadoList = List.of();
        when(empleadoService.findAllEmpleados()).thenReturn(empleadoList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/empleados/all/")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }


    @Test
    public void siInvocoGetAllEmpleadosActivosYExistenEmpleadosActivosRetornaListaEmpleadoYStatusOk() throws Exception {
        // Arrange
        Empleado empleado = getEmplados().get(0);
        List<Empleado> empleadoList = List.of(empleado);
        when(empleadoService.findAllEmpleadosActivos()).thenReturn(empleadoList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/empleados/")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jsonEmpleadoList.write(empleadoList).getJson(), response.getContentAsString());
    }

    @Test
    public void siInvocoGetAllEmpleadosActivosYYNoExistenEmpleadosActivosRetornaListaVaciaYStatusStatusNotFound() throws Exception {
        // Arrange
        List<Empleado> empleadoList = List.of();
        when(empleadoService.findAllEmpleadosActivos()).thenReturn(empleadoList);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/empleados/")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void siInvocoewEmpleadoYSePuedeGrabarRetornarStatusCreated() throws Exception {
        // Arrange
        Empleado empleado = getEmplados().get(0);
        when(empleadoService.save(any(Empleado.class))).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/empleados/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonEmpleado.write(empleado).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }


    @Test
    public void siInvocoNewEmpleadoYNoSePuedeGrabarRetornarBadRequest() throws Exception {
        // Arrange
        Empleado empleado = getEmplados().get(0);
        when(empleadoService.save(any(Empleado.class))).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/empleados/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonEmpleado.write(empleado).getJson())
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }


    @Test
    public void siInvocoGetEmpleadoByRUTYExisteEmpleadoConEseRutRetornaEmpleadoYStatusOk() throws Exception {
        // Arrange
        Empleado empleado = getEmplados().get(0);
        Optional<Empleado> optionalEmpleado = Optional.of(empleado);
        when(empleadoService.findEmpleadoByRUT(empleado.getRutEmpleado())).thenReturn(optionalEmpleado);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/empleados/" + empleado.getRutEmpleado())
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jsonEmpleado.write(empleado).getJson(), response.getContentAsString());
    }

    @Test
    public void siInvocoGetEmpleadoByRUTYNoExisteEmpleadoConEseRutRetornaStatusNotFound() throws Exception {
        // Arrange
        Optional<Empleado> optionalEmpleado = Optional.empty();
        when(empleadoService.findEmpleadoByRUT("111111111")).thenReturn(optionalEmpleado);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/empleados/111111111")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }


    @Test
    public void siInvocoDeleteEmpleadoByIdYSePuedeBorrarElEmpleadoRetornaStatusOk() throws Exception {
        // Arrange
        Empleado empleado = getEmplados().get(0);
        when(empleadoService.deleteEmpleadoById(empleado.getId_empleado())).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/empleados/" + empleado.getId_empleado())
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    @Test
    public void siInvocoDeleteVehiculoByIDYNoSePuedeBorrarElVehiculoRetornaStatusNotFound() throws Exception {
        // Arrange
        when(empleadoService.deleteEmpleadoById(1)).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/empleados/1/")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }


    @Test
    public void siInvocoSwitchEmpleadoEstadoYSePuedeCambiarElEstadoRetornaStatusOk() throws Exception {
        // Arrange
        Empleado empleado = getEmplados().get(0);
        when(empleadoService.SwitchEstado(empleado.getId_empleado())).thenReturn(true);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put("/empleados/switch/" + empleado.getId_empleado())
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    @Test
    public void siInvocoSwitchEmpleadoEstadoYNoSePuedeCambiarElEstadoRetornaStatusOk() throws Exception {
        // Arrange
        Empleado empleado = getEmplados().get(0);
        when(empleadoService.SwitchEstado(1)).thenReturn(false);

        // Act
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put("/empleados/switch/1")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }




    private List<Empleado> getEmplados() {
        List<Empleado> empleados = new ArrayList<>();
        Empleado empleado = new Empleado();
        empleado.setId_empleado(1);
        empleado.setRutEmpleado("111111111");
        // Le he quitado el acento a "Mármol" porque las pruebas no quedan bien
        empleado.setNombreEmpleado("Pablo Marmol");
        empleado.setTelefonoEmpleado(123546753);
        empleado.setCargo("Encargado");
        empleado.setPlanta(0);
        empleado.setActivo(true);
        empleados.add(empleado);
        empleado = new Empleado();
        empleado.setId_empleado(2);
        empleado.setRutEmpleado("2222222222");
        empleado.setNombreEmpleado("Homero Simpson");
        empleado.setTelefonoEmpleado(953723451);
        empleado.setCargo("Jefe");
        empleado.setPlanta(0);
        empleado.setActivo(true);
        empleados.add(empleado);
        empleado = new Empleado();
        empleado.setId_empleado(3);
        empleado.setRutEmpleado("3333333333");
        empleado.setNombreEmpleado("Yamcha");
        empleado.setTelefonoEmpleado(953723451);
        empleado.setCargo("Guardia");
        empleado.setPlanta(0);
        empleado.setActivo(false);
        empleados.add(empleado);
        return empleados;
    }
}
