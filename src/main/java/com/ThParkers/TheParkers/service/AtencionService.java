package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.dummy.AtencionNueva;
import com.ThParkers.TheParkers.model.Atencion;
import com.ThParkers.TheParkers.model.Boleta;
import com.ThParkers.TheParkers.model.Cliente;
import com.ThParkers.TheParkers.model.Vehiculo;
import com.ThParkers.TheParkers.repository.*;
import org.springframework.stereotype.Service;
import com.ThParkers.TheParkers.dummy.EmailDetails;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class AtencionService {
    private ClienteRepository clienteRepository;
    private VehiculoRepository vehiculoRepository;
    private AtencionRepository atencionRepository;
    private EstacionamientoService estacionamientoService;
    private PlantaRepository plantaRepository;
    private TipoVehiculoService tipoVehiculoService;
    private BoletaRepository boletaRepository;
    private PlantaService plantaService;
    private EmailServiceImpl emailService;

    public AtencionService(AtencionRepository atencionRepository, ClienteRepository clienteRepository,
                           VehiculoRepository vehiculoRepository, EstacionamientoService estacionamientoService,
                           PlantaRepository plantaRepository, TipoVehiculoService tipoVehiculoService,
                           BoletaRepository boletaRepository, PlantaService plantaService,
                           EmailServiceImpl emailService) {
        this.atencionRepository = atencionRepository;
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.estacionamientoService = estacionamientoService;
        this.plantaRepository = plantaRepository;
        this.tipoVehiculoService = tipoVehiculoService;
        this.boletaRepository = boletaRepository;
        this.plantaService = plantaService;
        this.emailService = emailService;
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
        int idPlanta = 4;
        int idEstacionamiento = estacionamientoService.devolverIdEstacionamiento(idPlanta,atencionNueva.getNronivel(),atencionNueva.getNroEstacionamiento());
        Optional<Cliente> optionalCliente = clienteRepository.findClienteByRutCliente(atencionNueva.getRutCliente());
        Optional<Vehiculo> optionalVehiculo = vehiculoRepository.findVehiculoBypatente(atencionNueva.getPatente());
        if (optionalCliente.isPresent() && optionalVehiculo.isPresent() && estacionamientoService.isAvailable(idEstacionamiento)){
            continuar = true;
        }
        if (continuar) {
            int id_cliente = optionalCliente.get().getIdCliente();
            int id_vehiculo = optionalVehiculo.get().getIdVehiculo();
            Atencion atencion = new Atencion();
            atencion.setHay_lavado(atencionNueva.isHay_lavado());
            atencion.setHoraEntrada(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime()));
            atencion.setIdCliente(id_cliente);
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

    public boolean atencionFuera (int idAtencion) throws ParseException {
        Optional<Atencion> atencionOptional = atencionRepository.findById(idAtencion);
        if (atencionOptional.isPresent()){
            Atencion atencionTemporal = atencionOptional.get();
            atencionTemporal.setHoraSalida(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime()));
            estacionamientoService.disponibilizarEstacionamiento(atencionTemporal.getId_estacionamiento());
            generarBoleta(atencionTemporal);
            atencionRepository.saveAndFlush(atencionTemporal);
            EmailDetails detalles = new EmailDetails();
            int cliente = atencionTemporal.getIdCliente();
            String email = clienteRepository.findClienteByIdCliente(cliente).get().getCorreo();
            String nombre = clienteRepository.findClienteByIdCliente(cliente).get().getNombreCliente();
            String patente = vehiculoRepository.findVehiculoByIdVehiculo(atencionTemporal.getId_vehiculo()).get().getPatente();
            String hora = atencionTemporal.getHoraSalida();
            String[] fechaHoraPartida = hora.split(" ");
            String[] fechaPartida = fechaHoraPartida[0].split("-");
            detalles.setRecipient(email);
            Month mes = Month.of(Integer.parseInt(fechaPartida[1]));
            String horaImprimir = "el " + fechaPartida[2] + " de " + mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES")).toLowerCase()
                    + " de " + fechaPartida[0] + " a las " + fechaHoraPartida[1];
            detalles.setSubject("Su vehículo ha salido");
            detalles.setMsgBody(nombre + ":\n\nSu vehículo con patente " + patente + " ha salido de nuestras instalaciones " +
                    horaImprimir + "\n\n¡Gracias por su preferencia!" );
            emailService.sendSimpleMail(detalles);
            return true;
        }
        return false;
    }

    public boolean generarBoleta (Atencion atencionTemporal) throws ParseException {
        int descuento = 0;
        int idPlanta = 4;
        Boleta boletaNueva = new Boleta();
        boletaNueva.setAtencionID(atencionTemporal.getAtencionID());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date Entrada = df.parse(atencionTemporal.getHoraEntrada());
        Date Salida = df.parse(atencionTemporal.getHoraSalida());
        int horas = hoursDifference(Salida,Entrada);
        //La primera hora se cobra sí o sí
        if (horas < 1) {
            horas = 1;
        }
        //El id de la planta se debería obtener a través de la sesión. Dado que no está implementada, haremos
        //de cuenta de que solo trabajamos con la planta 3

        int tarifaPlanta = (plantaRepository.findById(idPlanta).get().getTarifa_planta());
        int tipoVehiculo = (vehiculoRepository.findById(atencionTemporal.getId_vehiculo())).get().getId_tipoVehiculo();
        int tarifaVehiculo = tipoVehiculoService.devolverTarifa(tipoVehiculo);
        int subtotal = (tarifaPlanta + tarifaVehiculo) * horas;
        // Aplicar el aumento según qué tan ocupado esté el estacionamiento
        subtotal = plantaService.getTarifaActual(idPlanta, subtotal);
        if (esClienteFrecuente(atencionTemporal.getIdCliente())){
            descuento = ((subtotal*20)/100);
        }
        boletaNueva.setSubtotal(subtotal);
        boletaNueva.setDescuento(descuento);
        boletaNueva.setTotal(subtotal-descuento);
        boletaRepository.saveAndFlush(boletaNueva);
        Optional<Boleta> boletaOptional = boletaRepository.findBoletaByAtencionID(atencionTemporal.getAtencionID());
        return boletaOptional.isPresent();
    }

    private static int hoursDifference(Date date1, Date date2) {
        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
    }

    public List<Atencion> findAtencionesPorIdCliente(int idCliente){
        return atencionRepository.findAtencionByIdCliente(idCliente);
    }

    public boolean esClienteFrecuente (int idCliente) throws ParseException {
        List<Atencion> atencionList = findAtencionesPorIdCliente(idCliente);
        int cuentaFrecuente = 0;
        // El cliente se ha atendido menos de tres veces en el estacionamiento
        if (atencionList.size() < 3) {
            return false;
        } else {
            // Calcular si las tres atenciones recuperadas fueron dentro de la semana
            for (int i=1; i <=3; i++) {
                if (diasDeDiferencia(atencionList.get(atencionList.size() - i).getHoraEntrada()) < 7) {
                    cuentaFrecuente++;
                }
            }
        }
        if (cuentaFrecuente == 3) {
            return true;
        } else {
            return false;
        }
    }


    public static long diasDeDiferencia(String horaEntrada) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date Entrada = df.parse(horaEntrada);
        Date actual = Calendar.getInstance().getTime();
        long diff = actual.getTime() - Entrada.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
