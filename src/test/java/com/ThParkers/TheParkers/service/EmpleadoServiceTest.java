package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.model.Empleado;
import com.ThParkers.TheParkers.repository.EmpleadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTest {
    @Mock
    private EmpleadoRepository empleadoRepository;
    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    public void siInvocoFindAllEmpleadosYExistenEmpleadosDebeRetornarListaDeEmpleados(){
        // Arrange
        List<Empleado> empleados = getEmplados();
        when(empleadoRepository.findAll()).thenReturn(empleados);

        // Act
        List<Empleado> resultado = empleadoService.findAllEmpleados();

        // Assert
        assertNotNull(resultado);
        assertEquals(empleados.size(),resultado.size());
        assertEquals(empleados.get(0),resultado.get(0));
    }

    @Test
    public void siInvocoFindAllEmpleadosYNoExistenEmpleadosDebeRetornarListaVacia(){
        // Arrange
        when(empleadoRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Empleado> resultado = empleadoService.findAllEmpleados();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void siInvocoSaveYSePuedeAlmacenarElObjetoDebeRetornarVerdadero(){
        // Arrange
        Empleado empleado = getEmplados().get(0);
        Optional<Empleado> empleadoOptional = Optional.of(empleado);
        when(empleadoRepository.findEmpleadoByRutEmpleado(empleado.getRutEmpleado())).thenReturn(empleadoOptional);

        // Act
        boolean resultado = empleadoService.save(empleado);

        // Assert
        assertTrue(resultado);
    }


    @Test
    public void siInvocoSaveYNoSePuedeAlmacenarElObjetoDebeRetornarFalso(){
        // Arrange
        Empleado empleado = getEmplados().get(0);
        Optional<Empleado> empleadoOptionalVacio = Optional.empty();
        when(empleadoRepository.findEmpleadoByRutEmpleado(empleado.getRutEmpleado())).thenReturn(empleadoOptionalVacio);

        // Act
        boolean resultado = empleadoService.save(empleado);

        // Assert
        assertFalse(resultado);
    }

    @Test
    public void siInvocoFindEmpleadoByRUTDebeRetornarOptionalConEmpleado(){
        // Arrange
        Empleado empleado = getEmplados().get(0);
        Optional<Empleado> empleadoOptional = Optional.of(empleado);
        when(empleadoRepository.findEmpleadoByRutEmpleado(empleado.getRutEmpleado())).thenReturn(empleadoOptional);

        // Act
        Optional<Empleado> resultado = empleadoService.findEmpleadoByRUT("111111111");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isPresent());
    }

    @Test
    public void siInvocoFindEmpleadoByRUTYNoEstaElEmpleadoDebeRetornarOptionalVacio(){
        // Arrange
        List<Empleado> empleados = getEmplados();
        Optional<Empleado> empleadoOptionalVacio = Optional.empty();
        when(empleadoRepository.findEmpleadoByRutEmpleado(empleados.get(0).getRutEmpleado())).thenReturn(empleadoOptionalVacio);

        // Act
        Optional<Empleado> resultado = empleadoService.findEmpleadoByRUT("111111111");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    @Test
    public void siInvocoDeleteEmpleadoByIdYElEmpleadoSeEliminaDebeRetornarVerdadero(){
        // Arrange
        Empleado empleado = getEmplados().get(0);
        Optional<Empleado> empleadoOptional = Optional.of(empleado);
        when(empleadoRepository.findById(empleado.getId_empleado())).thenReturn(empleadoOptional);

        // Act
        boolean resultado = empleadoService.deleteEmpleadoById(1);

        // Assert
        assertTrue(resultado);
    }

    @Test
    public void siInvocoDeleteEmpleadoByIdYElEmpleadoNoExisteDebeRetornarFalso(){
        // Arrange
        Empleado empleado = getEmplados().get(0);
        Optional<Empleado> empleadoOptionalVacio = Optional.empty();
        when(empleadoRepository.findById(empleado.getId_empleado())).thenReturn(empleadoOptionalVacio);

        // Act
        boolean resultado = empleadoService.deleteEmpleadoById(1);

        // Assert
        assertFalse(resultado);
    }
    @Test
    public void siInvocoFindAllEmpleadosActivosYExistenEmpleadosActivosDebeRetornarListaDeEmpleados(){
        // Arrange
        List<Empleado> empleados = getEmplados();
        when(empleadoRepository.findEmpleadoByActivo(true)).thenReturn(empleados);

        // Act
        List<Empleado> resultado = empleadoService.findAllEmpleadosActivos();

        // Assert
        assertNotNull(resultado);
        assertEquals(empleados.size(),resultado.size());
        assertEquals(empleados.get(0),resultado.get(0));
    }

    @Test
    public void siInvocoFindAllEmpleadosActivosYNoExistenEmpleadosActivosDebeRetornarListaVacia(){
        // Arrange
        when(empleadoRepository.findEmpleadoByActivo(true)).thenReturn(new ArrayList<>());

        // Act
        List<Empleado> resultado = empleadoService.findAllEmpleadosActivos();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void siInvocoSwitchEstadoYEsteEstaActivoDebeDevolverTrueYCambiarEstadoAFalse(){
        // Arrange
        Empleado empleado = getEmplados().get(0);
        Optional<Empleado> empleadoOptional = Optional.of(empleado);
        when(empleadoRepository.findById(empleado.getId_empleado())).thenReturn(empleadoOptional);

        // Act
        boolean resultado = empleadoService.SwitchEstado(1);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado);
        assertFalse(empleado.isActivo());
    }

    @Test
    public void siInvocoSwitchEstadoYEsteEstaInactivoDebeDevolverTrueYCambiarEstadoATrue(){
        // Arrange
        Empleado empleado = getEmplados().get(2);
        Optional<Empleado> empleadoOptional = Optional.of(empleado);
        when(empleadoRepository.findById(empleado.getId_empleado())).thenReturn(empleadoOptional);

        // Act
        boolean resultado = empleadoService.SwitchEstado(3);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado);
        assertTrue(empleado.isActivo());
    }

    @Test
    public void siInvocoSwitchEstadoYElEmpleadoNoExisteDebeDevolverFalse(){
        // Arrange
        Empleado empleado = getEmplados().get(2);
        Optional<Empleado> empleadoOptionalVacio = Optional.empty();
        when(empleadoRepository.findById(empleado.getId_empleado())).thenReturn(empleadoOptionalVacio);

        // Act
        boolean resultado = empleadoService.SwitchEstado(3);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado);
    }



    private List<Empleado> getEmplados() {
        List<Empleado> empleados = new ArrayList<>();
        Empleado empleado = new Empleado();
        empleado.setId_empleado(1);
        empleado.setRutEmpleado("111111111");
        empleado.setNombreEmpleado("Pablo Mármol");
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
