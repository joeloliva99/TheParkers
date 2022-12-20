package com.TheParkers.TheParkers.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lavado")
public class Lavado {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_lavado;
	private String rutEmpleado;
	
	public int getId_lavado() {
		return id_lavado;
	}
	public void setId_lavado(int id_lavado) {
		this.id_lavado = id_lavado;
	}
	public String getRutEmpleado() {
		return rutEmpleado;
	}
	public void setRutEmpleado(String rutEmpleado) {
		this.rutEmpleado = rutEmpleado;
	}		
}
