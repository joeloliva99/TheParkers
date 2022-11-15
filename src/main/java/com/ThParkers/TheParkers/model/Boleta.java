package com.ThParkers.TheParkers.model;

import javax.persistence.*;

@Entity
@Table(name = "boleta")
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_boleta;
    private int atencionID;
    private int subtotal;
    private int descuento;
    private int total;

    public int getId_boleta() {
        return id_boleta;
    }

    public void setId_boleta(int id_boleta) {
        this.id_boleta = id_boleta;
    }

    public int getAtencionID() {
        return atencionID;
    }

    public void setAtencionID(int atencionID) {
        this.atencionID = atencionID;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
