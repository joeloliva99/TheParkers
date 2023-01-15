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
        // Indicador de si se realiza la atención o no (por defecto no)
        boolean continuar = false;
        //Este dato debería ser extraído desde la sesión de usuario. Dado que no está implementada, supondremos el funcionamiento con un dato ingresado a mano.
        int id_empleado = 5;
        //Este dato se debería obtener a través de la serión del usuario; no sería seguro que el propio usuario digitara la planta,
        //puesto que podría controlar la disponibilidad de estacionamientos en otras plantas.
        int idPlanta = 4;
        // Obtener el ID del estacionamiento (en el Json solo se ingresa el número y el nivel donde se encuentra)
        int idEstacionamiento = estacionamientoService.devolverIdEstacionamiento(idPlanta,atencionNueva.getNronivel(),atencionNueva.getNroEstacionamiento());
        // Recuperar el cliente desde la base de datos
        Optional<Cliente> optionalCliente = clienteRepository.findClienteByRutCliente(atencionNueva.getRutCliente());
        // Recuperar el vehículo desde la base de datos
        Optional<Vehiculo> optionalVehiculo = vehiculoRepository.findVehiculoBypatente(atencionNueva.getPatente());
        // Si el cliente y el vehículo existen, además el estacionamiento está disponible, se autotiza la atención
        if (optionalCliente.isPresent() && optionalVehiculo.isPresent() && estacionamientoService.isAvailable(idEstacionamiento)){
            continuar = true;
        }
        // Comprobar si se hace o no la atención
        if (continuar) {
            // Obtener el ID del cliente
            int id_cliente = optionalCliente.get().getIdCliente();
            // Obtener el ID del vehículo
            int id_vehiculo = optionalVehiculo.get().getIdVehiculo();
            // Crear un objeto "Atención" (que está acorde al modelo que se almacenará en base de datos)
            Atencion atencion = new Atencion();
            // Establecer los diversos atributos del objeto (solicitados a través del Json)
            atencion.setHay_lavado(atencionNueva.isHay_lavado());
            atencion.setHoraEntrada(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime()));
            atencion.setIdCliente(id_cliente);
            atencion.setId_empleado(id_empleado);
            atencion.setId_vehiculo(id_vehiculo);
            atencion.setId_estacionamiento(idEstacionamiento);
            // Ocupar el estacionamiento
            estacionamientoService.ocuparInutilizarEstacionamiento(idEstacionamiento);
            // Guardar la atención en la base de datos
            atencionRepository.saveAndFlush(atencion);
            // Comrobar si la atención se ingresó a la base de datos (mediante la hora)
            Optional<Atencion> atencionOptional = atencionRepository.findAtencionByHoraEntrada(atencion.getHoraEntrada());
            return atencionOptional.isPresent();
        } else {
            return false;
        }
    }

    public boolean atencionFuera (int idAtencion) throws ParseException {
        Optional<Atencion> atencionOptional = atencionRepository.findById(idAtencion);
        // Verificar si la atención existe
        if (atencionOptional.isPresent()){
            // Añadir la atención a un objeto "Atención" de forma temporal
            Atencion atencionTemporal = atencionOptional.get();
            // Añadirle la hora de salida al objeto (hasta este punto era un null). La hora proviene del sistema
            atencionTemporal.setHoraSalida(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime()));
            // Liberar el cupo de estacionamiento que había sido ocupado anteriomente
            estacionamientoService.disponibilizarEstacionamiento(atencionTemporal.getId_estacionamiento());
            // LLamar la función que genera y guarda la boleta
            generarBoleta(atencionTemporal);
            // Actualizar la atención en la base de datos
            atencionRepository.saveAndFlush(atencionTemporal);
            // Crear un nuevo objeto con los detalles del correo que se enviará (destinatario, asunto y mensaje)
            EmailDetails detalles = new EmailDetails();
            // Obtener el ID del cliente
            int cliente = atencionTemporal.getIdCliente();
            // Obtener el correo del cliente
            String email = clienteRepository.findClienteByIdCliente(cliente).get().getCorreo();
            // Obtener el nombre del cliente
            String nombre = clienteRepository.findClienteByIdCliente(cliente).get().getNombreCliente();
            // Obtener la patente del vehículo
            String patente = vehiculoRepository.findVehiculoByIdVehiculo(atencionTemporal.getId_vehiculo()).get().getPatente();
            // Obtener el datetime de la salida del vehículo
            String hora = atencionTemporal.getHoraSalida();
            // Partir el datetime en "date" y "time"
            String[] fechaHoraPartida = hora.split(" ");
            // Partir el date en "año", "mes" y "fecha"
            String[] fechaPartida = fechaHoraPartida[0].split("-");
            // Añadir el email del cliente al objeto con los detalles
            detalles.setRecipient(email);
            // Que el mes salga como palabra en vez de número
            Month mes = Month.of(Integer.parseInt(fechaPartida[1]));
            // el datetime a enviar, pero ahora con palabras (el 1 de enero de 2023).
            // La función "Locale" es para que el mes quede en español. "toLowerCase" para que quede totalmente en minúscula (lo correcto según la RAE)
            String horaImprimir = "el " + fechaPartida[2] + " de " + mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES")).toLowerCase()
                    + " de " + fechaPartida[0] + " a las " + fechaHoraPartida[1];
            // Añadir el asunto al objeto con los detalles
            detalles.setSubject("Su vehículo ha salido");
            // Añadir el mensaje en sí al objeto con los detalles (incluye el "datetime" en palabras descrito antes
            // y la patente obtenida anteriormente
            detalles.setMsgBody(nombre + ":\n\nSu vehículo con patente " + patente + " ha salido de nuestras instalaciones " +
                    horaImprimir + "\n\n¡Gracias por su preferencia!" );
            // Usar el servicio "emailService" para enviar el mail
            emailService.sendSimpleMail(detalles);
            return true;
        }
        return false;
    }

    // Función que genera la boleta
    public boolean generarBoleta (Atencion atencionTemporal) throws ParseException {
        int descuento = 0;
        // El id de la planta se debería obtener a través de la sesión. Dado que no está implementada, haremos
        //de cuenta de que solo trabajamos con la planta 3
        int idPlanta = 4;
        // Crear un nuevo objeto "Boleta" para almecenar en la base de datos
        Boleta boletaNueva = new Boleta();
        // Añadirle el ID de la atención a la boleta
        boletaNueva.setAtencionID(atencionTemporal.getAtencionID());
        // Crear el formato para el datetime
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // Obtener el datetime de la entrada al estacionamiento
        Date Entrada = df.parse(atencionTemporal.getHoraEntrada());
        // Obtener el datetime de la salida al estacionamiento
        Date Salida = df.parse(atencionTemporal.getHoraSalida());
        // Calcular la cantidad de horas que el vehículo estuvo en las instalaciones
        // Esto se obtiene restando la hora de salida con la de la entrada
        int horas = hoursDifference(Salida,Entrada);
        //La primera hora se cobra sí o sí
        if (horas < 1) {
            horas = 1;
        }
        // Obtener la tarifa de la planta
        int tarifaPlanta = (plantaRepository.findById(idPlanta).get().getTarifa_planta());
        // Obtener el ID del tipo de vehículo (obtenido en base al vehículo que sale). Esto servirá para
        // calcular la tarifa adicional acorde al tipo de vehículo
        int tipoVehiculo = (vehiculoRepository.findById(atencionTemporal.getId_vehiculo())).get().getId_tipoVehiculo();
        // Obtener la tarifa del vehículo usando el ID obtenido previamente
        int tarifaVehiculo = tipoVehiculoService.devolverTarifa(tipoVehiculo);
        // Sumar ambas tarifas para obtener el subtotal
        int subtotal = (tarifaPlanta + tarifaVehiculo) * horas;
        // Aplicar el aumento según qué tan ocupado esté el estacionamiento
        subtotal = plantaService.getTarifaActual(idPlanta, subtotal);
        // Verificar si es cliente frecuente. En caso de serlo, aplicar un 20 % de descuento
        if (esClienteFrecuente(atencionTemporal.getIdCliente())){
            descuento = ((subtotal*20)/100);
        }
        // Añadir el subtotal a la boleta (puede incluir el incremento dependiendo de qué tan lleno esté el estacionamiento)
        boletaNueva.setSubtotal(subtotal);
        // Añadir el descuento a la boleta (calculado anteriormente)
        boletaNueva.setDescuento(descuento);
        // Añadir el total (diferencia entre el subtotal y el descuento) a la boleta
        boletaNueva.setTotal(subtotal-descuento);
        // Guardar la boleta en la base de datos
        boletaRepository.saveAndFlush(boletaNueva);
        // Comprobar si la boleta se guardó usando el ID de la atención
        Optional<Boleta> boletaOptional = boletaRepository.findBoletaByAtencionID(atencionTemporal.getAtencionID());
        return boletaOptional.isPresent();
    }

    // Función que calcula cuántas horas de diferencia hay entre el datetime a y datetime b
    private static int hoursDifference(Date date1, Date date2) {
        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
    }

    public List<Atencion> findAtencionesPorIdCliente(int idCliente){
        return atencionRepository.findAtencionByIdCliente(idCliente);
    }

    // Esta función determina si un cliente es frecuente o no
    // (debe haber entrado al estacionamiento al menos tres veces en la semana)
    public boolean esClienteFrecuente (int idCliente) throws ParseException {
        // Obtener la lista con las atenciones que ha tenido el cliente en el estacionamiento
        List<Atencion> atencionList = findAtencionesPorIdCliente(idCliente);
        int cuentaFrecuente = 0;
        // Calcular el tamaño de la lista; si es menor a 3, signifca que el cliente se ha atendido menos de tres veces,
        // por ende, no es frecuente
        if (atencionList.size() < 3) {
            return false;
        } else {
            // Si las tres últimas atenciones fueron dentro de la semana (o sea, hay menos de 7 días de diferencia
            // entre hoy y la fecha en la que se el cliente usó el servicio), meter al contador de atenciones
            for (int i=1; i <=3; i++) {
                if (diasDeDiferencia(atencionList.get(atencionList.size() - i).getHoraEntrada()) < 7) {
                    cuentaFrecuente++;
                }
            }
        }
        // Si el contador de atenciones es igual a tres, se retorna true
        if (cuentaFrecuente == 3) {
            return true;
        } else {
            return false;
        }
    }

    // Contar los días de diferencia entre dos fechas dadas
    public static long diasDeDiferencia(String horaEntrada) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date Entrada = df.parse(horaEntrada);
        Date actual = Calendar.getInstance().getTime();
        long diff = actual.getTime() - Entrada.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
