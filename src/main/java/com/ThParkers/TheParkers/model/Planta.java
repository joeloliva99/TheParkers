package com.ThParkers.TheParkers.model;

import javax.persistence.*;

@Entity
@Table(name = "planta")
public class Planta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_planta;
    private String direccion;
    private int tarifa_planta;
    private Integer id_encargado;

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

    public int getTarifa_planta() {
        return tarifa_planta;
    }

    public void setTarifa_planta(int tarifa_planta) {
        this.tarifa_planta = tarifa_planta;
    }

    public Integer getId_encargado() {
        return id_encargado;
    }

    public void setId_encargado(Integer id_encargado) {
        this.id_encargado = id_encargado;
    }
}
