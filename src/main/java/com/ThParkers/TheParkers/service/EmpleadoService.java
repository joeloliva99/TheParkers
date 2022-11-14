package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.model.Empleado;
import com.ThParkers.TheParkers.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {
    private EmpleadoRepository empleadoRepository;
    public EmpleadoService (EmpleadoRepository empleadoRepository) {
        this.empleadoRepository= empleadoRepository;
    }
    public List<Empleado> findAllEmpleados() {
        return empleadoRepository.findAll();
    }
}
