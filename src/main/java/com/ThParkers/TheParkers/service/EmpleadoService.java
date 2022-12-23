package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.model.Empleado;
import com.ThParkers.TheParkers.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {
    private EmpleadoRepository empleadoRepository;
    public EmpleadoService (EmpleadoRepository empleadoRepository) {
        this.empleadoRepository= empleadoRepository;
    }

    public List<Empleado> findAllEmpleados() {
        return empleadoRepository.findAll();
    }

    public boolean save(Empleado empleado) {
        empleadoRepository.saveAndFlush(empleado);
        Optional<Empleado> empleadoOptional = empleadoRepository.findEmpleadoByRutEmpleado(empleado.getRutEmpleado());
        return empleadoOptional.isPresent();
    }

    public Optional<Empleado> findEmpleadoByRUT(String rutCliente) {
        return empleadoRepository.findEmpleadoByRutEmpleado(rutCliente);
    }

    public boolean deleteEmpleadoById(int idEmp) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(idEmp);
        if (empleadoOptional.isPresent()) {
            empleadoRepository.deleteById(idEmp);
            return true;
        } else {
            return false;
        }
    }


    public boolean SwitchEstado(int idEmp) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(idEmp);
        if (empleadoOptional.isPresent()) {
            Empleado empleado = empleadoOptional.get();
            if (empleado.isActivo()) {
                empleado.setActivo(false);
            } else {
                empleado.setActivo(true);
            }
            empleadoRepository.saveAndFlush(empleado);
            return true;
        } else {
            return false;
        }
    }
    public List<Empleado> findAllEmpleadosActivos() {
        return empleadoRepository.findEmpleadoByActivo(true);
    }
}
