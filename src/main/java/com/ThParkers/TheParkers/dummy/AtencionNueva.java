package com.ThParkers.TheParkers.dummy;

import org.springframework.format.annotation.DateTimeFormat;

public class AtencionNueva {
    private String rutCliente;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private String horaEntrada;
    private String patente;
    private int nronivel;
    private int nroEstacionamiento;
    private boolean hay_lavado;

    public String getRutCliente() {
        return rutCliente;
    }

    public void setRutCliente(String rutCliente) {
        this.rutCliente = rutCliente;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public int getNronivel() {
        return nronivel;
    }

    public void setNronivel(int nronivel) {
        this.nronivel = nronivel;
    }

    public int getNroEstacionamiento() {
        return nroEstacionamiento;
    }

    public void setNroEstacionamiento(int nroEstacionamiento) {
        this.nroEstacionamiento = nroEstacionamiento;
    }

    public boolean isHay_lavado() {
        return hay_lavado;
    }

    public void setHay_lavado(boolean hay_lavado) {
        this.hay_lavado = hay_lavado;
    }
}
