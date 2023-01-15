package com.TheParkers.TheParkers.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "empleado")
public class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_empleado;
	private String rutEmpleado;
	private String nombreEmpleado;
	private int telefonoEmpleado;
	private int planta;
	//private int departamentoID;
	
	@ManyToOne
	private Departamento departamento;
	
	public int getId_empleado() {
		return id_empleado;
	}
	public void setId_empleado(int id_empleado) {
		this.id_empleado = id_empleado;
	}
	public String getRutEmpleado() {
		return rutEmpleado;
	}
	public void setRutEmpleado(String rutEmpleado) {
		this.rutEmpleado = rutEmpleado;
	}
	public String getNombreEmpleado() {
		return nombreEmpleado;
	}
	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}
	public int getTelefonoEmpleado() {
		return telefonoEmpleado;
	}
	public void setTelefonoEmpleado(int telefonoEmpleado) {
		this.telefonoEmpleado = telefonoEmpleado;
	}
	public int getPlanta() {
		return planta;
	}
	public void setPlanta(int planta) {
		this.planta = planta;
	}
	/*public int getDepartamentoID() {
		return departamentoID;
	}
	public void setDepartamentoID(int departamentoID) {
		this.departamentoID = departamentoID;
	}*/
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
}
