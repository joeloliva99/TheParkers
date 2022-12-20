package com.TheParkers.TheParkers.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "atencion")
public class Atencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int atencionID;
    @Column(name="id_cliente")
    private int idCliente;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private String horaEntrada;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private String horaSalida;
    private int id_vehiculo;
    private int id_empleado;
    private int id_estacionamiento;
    private boolean hay_lavado;
    private Integer id_lavado;
	public int getAtencionID() {
		return atencionID;
	}
	public void setAtencionID(int atencionID) {
		this.atencionID = atencionID;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getHoraEntrada() {
		return horaEntrada;
	}
	public void setHoraEntrada(String horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	public String getHoraSalida() {
		return horaSalida;
	}
	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}
	public int getId_vehiculo() {
		return id_vehiculo;
	}
	public void setId_vehiculo(int id_vehiculo) {
		this.id_vehiculo = id_vehiculo;
	}
	public int getId_empleado() {
		return id_empleado;
	}
	public void setId_empleado(int id_empleado) {
		this.id_empleado = id_empleado;
	}
	public int getId_estacionamiento() {
		return id_estacionamiento;
	}
	public void setId_estacionamiento(int id_estacionamiento) {
		this.id_estacionamiento = id_estacionamiento;
	}
	public boolean isHay_lavado() {
		return hay_lavado;
	}
	public void setHay_lavado(boolean hay_lavado) {
		this.hay_lavado = hay_lavado;
	}
	public Integer getId_lavado() {
		return id_lavado;
	}
	public void setId_lavado(Integer id_lavado) {
		this.id_lavado = id_lavado;
	}
    
}
