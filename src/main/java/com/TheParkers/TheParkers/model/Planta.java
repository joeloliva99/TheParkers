package com.TheParkers.TheParkers.model;

import javax.persistence.*;


@Entity
@Table(name = "planta")
public class Planta {
	
	private int id_planta;
	private String direccion;
	private String id_encargado;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId_planta() {
		return id_planta;
	}
	
	public void setId_planta(int id_planta) {
		this.id_planta = id_planta;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getId_encargado() {
		return id_encargado;
	}
	
	public void setId_encargado(String id_encargado) {
		this.id_encargado = id_encargado;
	}

}
