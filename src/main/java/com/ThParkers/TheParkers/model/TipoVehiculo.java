package com.ThParkers.TheParkers.model;


import javax.persistence.*;

@Entity
@Table(name = " tipo_vehiculo ")
public class TipoVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_tipoVehiculo;
    @Column(name = "nom_tipoVehiculo")
    private String NTipoVehiculo;
    private int tarifa;

    public int getId_tipoVehiculo() {
        return id_tipoVehiculo;
    }

    public void setId_tipoVehiculo(int id_tipoVehiculo) {
        this.id_tipoVehiculo = id_tipoVehiculo;
    }

    public String getNTipoVehiculo() {
        return NTipoVehiculo;
    }

    public void setNTipoVehiculo(String NTipoVehiculo) {
        this.NTipoVehiculo = NTipoVehiculo;
    }

    public int getTarifa() {
        return tarifa;
    }

    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }
}
