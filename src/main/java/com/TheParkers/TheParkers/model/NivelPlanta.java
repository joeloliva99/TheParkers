package com.TheParkers.TheParkers.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nivel_planta")
public class NivelPlanta {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_nivel;
	private int n_nivel;
	@Column(name = "id_planta")
	private int idPlanta;
	
	public int getId_nivel() {
		return id_nivel;
	}
	public void setId_nivel(int id_nivel) {
		this.id_nivel = id_nivel;
	}
	public int getN_nivel() {
		return n_nivel;
	}
	public void setN_nivel(int n_nivel) {
		this.n_nivel = n_nivel;
	}
	public int getIdPlanta() {
		return idPlanta;
	}
	public void setIdPlanta(int idPlanta) {
		this.idPlanta = idPlanta;
	}
	
	
	
}
