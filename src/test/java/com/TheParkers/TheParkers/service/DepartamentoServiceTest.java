package com.TheParkers.TheParkers.service;

import com.TheParkers.TheParkers.model.Departamento;
import com.TheParkers.TheParkers.model.Lavado;
import com.TheParkers.TheParkers.repository.DepartamentoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@EstendWith(MockitoExtension.class)
class DepartamentoServiceTest {

    @Mock
    private DepartamentoRepository departamentoRepository;
    @InjectMocks
    private DepartamentoServiceImpl departamentoService;


    //Lista de departamentos
    @Test
    public void buscarTodosLosDepartamentosExistentes() {
        //Arrange
        List<Departamento> departamentos = getDepartamentos();
        when(departamentoRepository.findAll().thenReturn(departamentos));
        //Act
        List<Departamento> resultado = departamentoService.buscarTodosLosDepartamentos();
        //Assert
    assertNotNull(resultado);
    assertEquals(departamentos.size(),resultado.size());
    assertEquals(departamentos.get(0),resultado.get(0));
    }

    private List<Departamento> getDepartamentos() {
        List<Departamento> departamentos = new ArrayList<>();
        Departamento departamento = new Departamento();
        departamento.setDepartamentoID(1);
        departamento.setNombreDepartamento("administracion");
        departamentos.add(departamento);

        return departamentos;
    }


    @Test
    public void buscarTodosLosDepartamentosSinResultados() {
       //Arrange
        when(departamentoRepository.findAll().thenReturn(ArrayList<>());

        //Act
        List<Departamento> resultado = departamentoService.buscarTodosLosDepartamentos();

        //Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

    }  2
    @Test
    public void findDepartamentoById() {
        //Arrange
        Departamento departamento = getDepartamentosPorId(1);
        Optional<Departamento> departamentoOptional = Optional.of(departamento);
        when(departamentoRepository.findById(departamento.getDepartamentoID())).thenReturn(departamentoOptional);
        //Act
        Departamento departamento = departamentoService.findDepartamentoById(departamento.getDepartamentoID());


    }

    private Departamento getDepartamentosPorId() {
    }

    @Test
    void guardarDepartamento() {
    }

    @Test
    void borrarDepartamentoPorId() {
    }
}