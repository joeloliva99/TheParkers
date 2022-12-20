package com.TheParkers.TheParkers.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estacionamiento")
public class Estacionamiento {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_estacionamiento;
	private int nro_estacionamiento;
	@Column(name="id_nivel")
	private int idNivel;
	private boolean disponible;
	
	
	public int getId_estacionamiento() {
		return id_estacionamiento;
	}
	public void setId_estacionamiento(int id_estacionamiento) {
		this.id_estacionamiento = id_estacionamiento;
	}
	public int getNro_estacionamiento() {
		return nro_estacionamiento;
	}
	public void setNro_estacionamiento(int nro_estacionamiento) {
		this.nro_estacionamiento = nro_estacionamiento;
	}
	public int getIdNivel() {
		return idNivel;
	}
	public void setIdNivel(int idNivel) {
		this.idNivel = idNivel;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	
	
}
