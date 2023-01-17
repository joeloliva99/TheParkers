package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.dummy.AtencionNueva;
import com.ThParkers.TheParkers.model.Atencion;
import com.ThParkers.TheParkers.service.AtencionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/atenciones")
public class AtencionRestController {
    private AtencionService atencionService;

    public AtencionRestController(AtencionService atencionService) {
        this.atencionService = atencionService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Atencion>> getAllAtenciones (){
        List<Atencion> atencionList = atencionService.findAllAtenciones();
        if (!atencionList.isEmpty()){
            return new ResponseEntity<>(atencionList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Void> addAtencion(@RequestBody AtencionNueva atencionNueva) {
        boolean creado = atencionService.addAtencion(atencionNueva);
        if (creado){
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping(value = "/{idAtencion}")
    public ResponseEntity<Void> saleAuto(@PathVariable int idAtencion) throws ParseException {
        boolean salidaIsAlright = atencionService.atencionFuera(idAtencion);
        if (salidaIsAlright){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


}
