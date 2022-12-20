package com.TheParkers.TheParkers.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = " tipo_vehiculo ")
public class TipoVehiculo {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_tipoVehiculo;
    @Column(name = "nom_tipoVehiculo")
	private String NTipoVehiculo;
	private int tarifa;
	
	public int getId_tipoVehiculo() {
		return id_tipoVehiculo;
	}
	public void setId_tipoVehiculo(int id_tipoVehiculo) {
		this.id_tipoVehiculo = id_tipoVehiculo;
	}
	public String getNtipoVehiculo() {
		return NTipoVehiculo;
	}
	public void setNtipoVehiculo(String ntipoVehiculo) {
		NTipoVehiculo = ntipoVehiculo;
	}
	public String getNTipoVehiculo() {
		return NTipoVehiculo;
	}
	public void setNTipoVehiculo(String nTipoVehiculo) {
		NTipoVehiculo = nTipoVehiculo;
	}
	public int getTarifa() {
		return tarifa;
	}
	public void setTarifa(int tarifa) {
		this.tarifa = tarifa;
	}
}
