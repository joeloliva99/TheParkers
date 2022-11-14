package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.dummy.AtencionNueva;
import com.ThParkers.TheParkers.model.Atencion;
import com.ThParkers.TheParkers.model.Cliente;
import com.ThParkers.TheParkers.model.Vehiculo;
import com.ThParkers.TheParkers.repository.ClienteRepository;
import com.ThParkers.TheParkers.repository.VehiculoRepository;
import com.ThParkers.TheParkers.repository.AtencionRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class AtencionService {
    private ClienteRepository clienteRepository;
    private VehiculoRepository vehiculoRepository;
    private AtencionRepository atencionRepository;
    private EstacionamientoService estacionamientoService;

    public AtencionService(AtencionRepository atencionRepository, ClienteRepository clienteRepository, VehiculoRepository vehiculoRepository, EstacionamientoService estacionamientoService) {
        this.atencionRepository = atencionRepository;
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.estacionamientoService = estacionamientoService;
    }

    public List<Atencion> findAllAtenciones() {
        return atencionRepository.findAll();
    }

    public boolean addAtencion(AtencionNueva atencionNueva) {
        boolean continuar = false;
        //Este dato debería ser extraído desde la sesión de usuario. Dado que no está implementada, supondremos el funcionamiento con un dato ingresado a mano.
        int id_empleado = 5;
        //Este dato se debería obtener a través de la serión del usuario; no sería seguro que el propio usuario digitara la planta,
        //puesto que podría controlar la disponibilidad de estacionamientos en otras plantas.
        int idPlanta = 3;
        int idEstacionamiento = estacionamientoService.devolverIdEstacionamiento(idPlanta,atencionNueva.getNronivel(),atencionNueva.getNroEstacionamiento());
        Optional<Cliente> optionalCliente = clienteRepository.findClienteByRutCliente(atencionNueva.getRutCliente());
        Optional<Vehiculo> optionalVehiculo = vehiculoRepository.findVehiculoBypatente(atencionNueva.getPatente());
        if (optionalCliente.isPresent() && optionalVehiculo.isPresent() && estacionamientoService.isAvailable(idEstacionamiento)){
            continuar = true;
        }
        if (continuar) {
            int id_cliente = optionalCliente.get().getId_cliente();
            int id_vehiculo = optionalVehiculo.get().getId_vehiculo();
            Atencion atencion = new Atencion();
            atencion.setHay_lavado(atencionNueva.isHay_lavado());
            atencion.setHoraEntrada(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime()));
            atencion.setId_cliente(id_cliente);
            atencion.setId_empleado(id_empleado);
            atencion.setId_vehiculo(id_vehiculo);
            atencion.setId_estacionamiento(idEstacionamiento);
            estacionamientoService.ocuparInutilizarEstacionamiento(idEstacionamiento);
            atencionRepository.saveAndFlush(atencion);
            Optional<Atencion> atencionOptional = atencionRepository.findAtencionByHoraEntrada(atencion.getHoraEntrada());
            return atencionOptional.isPresent();
        } else {
            return false;
        }
    }

    public boolean atencionFuera (int idAtencion) {
        Optional<Atencion> atencionOptional = atencionRepository.findById(idAtencion);
        if (atencionOptional.isPresent()){
            Atencion atencionTemporal = atencionOptional.get();
            atencionTemporal.setHoraSalida(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime()));
            estacionamientoService.disponibilizarEstacionamiento(atencionTemporal.getId_estacionamiento());
            atencionRepository.saveAndFlush(atencionTemporal);
            return true;
        }
        return false;
    }
}
