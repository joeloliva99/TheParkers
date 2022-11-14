package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.model.Empleado;
import com.ThParkers.TheParkers.service.EmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoRestController {

    private EmpleadoService empleadoService;

    public EmpleadoRestController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Empleado>> getAllEmpleados() {
        List<Empleado> empleadoList = empleadoService.findAllEmpleados();
        if (!empleadoList.isEmpty()){
            return new ResponseEntity<>(empleadoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
