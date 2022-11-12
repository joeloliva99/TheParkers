package com.TheParkers.TheParkers.model;

import javax.persistence.*; 


@Entity
@Table(name = "lavado")
public class Lavado {
	
	private int id_lavado;
	
	private String rutEmpleado;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
