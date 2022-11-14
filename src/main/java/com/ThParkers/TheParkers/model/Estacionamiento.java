package com.ThParkers.TheParkers.model;

import javax.persistence.*;

@Entity
@Table(name = "estacionamiento")
public class Estacionamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_estacionamiento ;
    private int nro_estacionamiento;
    @Column(name="id_nivel")
    private int idNivel;
    private boolean disponible;

    public int getId_estacionamiento() {
        return id_estacionamiento;
    }

    public void setId_estacionamiento(int id_estacionamiento) {
        this.id_estacionamiento = id_estacionamiento;
    }

    public int getNro_estacionamiento() {
        return nro_estacionamiento;
    }

    public void setNro_estacionamiento(int nro_estacionamiento) {
        this.nro_estacionamiento = nro_estacionamiento;
    }

    public int getId_nivel() {
        return idNivel;
    }

    public void setId_nivel(int id_nivel) {
        this.idNivel = id_nivel;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}

