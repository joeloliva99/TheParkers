package com.TheParkers.TheParkers.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dependiente")
public class Dependiente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_dependiente;
    
    private String rutEmpleado;
    private String rutDependiente;
    private String nombreDependiente;
    private String sexo;
    private String relacion;
    
    
	public int getId_dependiente() {
		return id_dependiente;
	}
	public void setId_dependiente(int id_dependiente) {
		this.id_dependiente = id_dependiente;
	}
	public String getRutEmpleado() {
		return rutEmpleado;
	}
	public void setRutEmpleado(String rutEmpleado) {
		this.rutEmpleado = rutEmpleado;
	}
	public String getNombreDependiente() {
		return nombreDependiente;
	}
	public void setNombreDependiente(String nombreDependiente) {
		this.nombreDependiente = nombreDependiente;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getRelacion() {
		return relacion;
	}
	public void setRelacion(String relacion) {
		this.relacion = relacion;
	}
	public String getRutDependiente() {
		return rutDependiente;
	}
	public void setRutDependiente(String rutDependiente) {
		this.rutDependiente = rutDependiente;
	}
    
    
}
