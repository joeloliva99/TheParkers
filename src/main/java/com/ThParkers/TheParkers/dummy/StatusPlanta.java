package com.ThParkers.TheParkers.dummy;

public class StatusPlanta {
    private int idPlanta;
    private int nroNiveles;
    private int totalEstac;
    private String proporLleno;
    private String estado;

    public int getIdPlanta() {
        return idPlanta;
    }

    public int getNroNiveles() {
        return nroNiveles;
    }

    public int getTotalEstac() {
        return totalEstac;
    }

    public String getProporLleno() {
        return proporLleno;
    }

    public String getEstado() {
        return estado;
    }

    public void setIdPlanta(int idPlanta) {
        this.idPlanta = idPlanta;
    }

    public void setNroNiveles(int nroNiveles) {
        this.nroNiveles = nroNiveles;
    }

    public void setTotalEstac(int totalEstac) {
        this.totalEstac = totalEstac;
    }

    public void setProporLleno(String proporLleno) {
        this.proporLleno = proporLleno;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
