package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.model.Empleado;
import com.ThParkers.TheParkers.service.EmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empleados")
public class EmpleadoRestController {

    private EmpleadoService empleadoService;

    public EmpleadoRestController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Empleado>> getAllEmpleados() {
        List<Empleado> empleadoList = empleadoService.findAllEmpleados();
        if (!empleadoList.isEmpty()){
            return new ResponseEntity<>(empleadoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Empleado>> getAllEmpleadosActivos() {
        List<Empleado> empleadoList = empleadoService.findAllEmpleadosActivos();
        if (!empleadoList.isEmpty()){
            return new ResponseEntity<>(empleadoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Void> newEmpleado(@RequestBody Empleado empleado) {
        boolean allright = empleadoService.save(empleado);
        if (allright){
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{RUTEmpleado}")
    public ResponseEntity<Empleado> getEmpleadoByRUT(@PathVariable String RUTEmpleado) {
        Optional<Empleado> empleadoOptional = empleadoService.findEmpleadoByRUT(RUTEmpleado);
        if (empleadoOptional.isPresent()) {
            return new ResponseEntity<>(empleadoOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(value = "/{idEmp}")
    public ResponseEntity<Void> deleteEmpleadoById(@PathVariable int idEmp) {
        boolean eliminado = empleadoService.deleteEmpleadoById(idEmp);
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/switch/{idEmp}")
    public ResponseEntity<Void> switchEmpleadoEstado(@PathVariable int idEmp) {
        boolean allright = empleadoService.SwitchEstado(idEmp);
        if (allright){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
