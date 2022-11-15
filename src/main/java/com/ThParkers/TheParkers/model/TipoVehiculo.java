package com.ThParkers.TheParkers.model;


import javax.persistence.*;

@Entity
@Table(name = " tipo_vehiculo ")
public class TipoVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_tipoVehiculo;
    private String nom_tipoVehiculo;
    private int tarifa;

    public int getId_tipoVehiculo() {
        return id_tipoVehiculo;
    }

    public void setId_tipoVehiculo(int id_tipoVehiculo) {
        this.id_tipoVehiculo = id_tipoVehiculo;
    }

    public String getNom_tipoVehiculo() {
        return nom_tipoVehiculo;
    }

    public void setNom_tipoVehiculo(String nom_tipoVehiculo) {
        this.nom_tipoVehiculo = nom_tipoVehiculo;
    }

    public int getTarifa() {
        return tarifa;
    }

    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }
}
