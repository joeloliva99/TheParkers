package com.TheParkers.TheParkers.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "departamento")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int departamentoID;
	private String nombreDepartamento;
	private int id_planta;
	private int encargadoID;
	
	@OneToMany(mappedBy = "empleado")
	List <Empleado> empleados;
	
	
	public Departamento(String nombreDepartamento, int id_planta, int encargadoID, String cargo) {
		super();
		this.nombreDepartamento = nombreDepartamento;
		this.id_planta = id_planta;
		this.encargadoID = encargadoID;
	}
	public int getDepartamentoID() {
		return departamentoID;
	}
	public void setDepartamentoID(int departamentoID) {
		this.departamentoID = departamentoID;
	}
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}
	public int getId_planta() {
		return id_planta;
	}
	public void setId_planta(int id_planta) {
		this.id_planta = id_planta;
	}
	public int getEncargadoID() {
		return encargadoID;
	}
	public void setEncargadoID(int encargadoID) {
		this.encargadoID = encargadoID;
	}
	public List<Empleado> getEmpleados() {
		return empleados;
	}
	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}
	
}
