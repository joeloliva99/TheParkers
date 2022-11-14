package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.model.Vehiculo;
import com.ThParkers.TheParkers.service.VehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoRestController {
    private VehiculoService vehiculoService;

    public VehiculoRestController (VehiculoService vehiculoService){
        this.vehiculoService = vehiculoService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Vehiculo>> getAllVehiculos() {
        List<Vehiculo> vehiculoList = vehiculoService.findAllVehiculos();
        if(!vehiculoList.isEmpty()){
            return new ResponseEntity<>(vehiculoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value = "")
    public ResponseEntity<Void> newVehiculo(@RequestBody Vehiculo vehiculo) {
        boolean allright = vehiculoService.save(vehiculo);
        if (allright){
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{Patente}")
    public ResponseEntity<Vehiculo> getVehiculoByPatente(@PathVariable String Patente) {
        Optional<Vehiculo> vehiculoOptional = vehiculoService.findVehiculoBypatente(Patente);
        if (vehiculoOptional.isPresent()) {
            return new ResponseEntity<>(vehiculoOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{idVehiculo}")
    public ResponseEntity<Void> deleteVehiculoByID(@PathVariable int idVehiculo){
        boolean eliminado = vehiculoService.deleteVehiculoById(idVehiculo);
        if (eliminado){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
