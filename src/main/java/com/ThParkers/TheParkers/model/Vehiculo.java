package com.ThParkers.TheParkers.model;

import javax.persistence.*;

@Entity
@Table(name = "vehiculo")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_vehiculo")
    private int idVehiculo;
    private String patente;
    private int id_tipoVehiculo;

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public int getId_tipoVehiculo() {
        return id_tipoVehiculo;
    }

    public void setId_tipoVehiculo(int id_tipoVehiculo) {
        this.id_tipoVehiculo = id_tipoVehiculo;
    }
}
