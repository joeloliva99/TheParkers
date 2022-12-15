package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.model.Planta;
import com.ThParkers.TheParkers.model.TipoVehiculo;
import com.ThParkers.TheParkers.model.Vehiculo;
import com.ThParkers.TheParkers.service.TipoVehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipovehiculos")
public class TipoVehiculoRestController {
    private TipoVehiculoService tipoVehiculoService;

    public TipoVehiculoRestController (TipoVehiculoService tipoVehiculoService) {
        this.tipoVehiculoService = tipoVehiculoService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<TipoVehiculo>> getAllTiposVehiculos() {
        List<TipoVehiculo> tipoVehiculoList = tipoVehiculoService.findAllTiposVehiculos();
        if(!tipoVehiculoList.isEmpty()){
            return new ResponseEntity<>(tipoVehiculoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value = "")
    public ResponseEntity<Void> newTipoVehiculo(@RequestBody TipoVehiculo tipoVehiculo) {
        boolean allright = tipoVehiculoService.save(tipoVehiculo);
        if (allright){
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{IDTipo}")
    public ResponseEntity<TipoVehiculo> getTipoVehiculoByID(@PathVariable int IDTipo) {
        Optional<TipoVehiculo> tipoVehiculoOptional = tipoVehiculoService.findTipoVehiculoById(IDTipo);
        if (tipoVehiculoOptional.isPresent()) {
            return new ResponseEntity<>(tipoVehiculoOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{idTipoVehiculo}")
    public ResponseEntity<Void> deleteTipoVehiculoByID(@PathVariable int idTipoVehiculo){
        boolean eliminado = tipoVehiculoService.deleteTipoVehiculoById(idTipoVehiculo);
        if (eliminado){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "")
    public ResponseEntity<Void> updateTipoVehiculo(@RequestBody TipoVehiculo tipoVehiculo) {
        boolean actualizado = tipoVehiculoService.update(tipoVehiculo);
        if (actualizado) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
