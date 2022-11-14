package com.ThParkers.TheParkers.dummy;

//Objeto falso; solo para recibir las instrucciones Json que yo solicito
// (vale decir, "id_planta" y "tarifa_planta", ya que nada más quiero actualizar la tarifa de una planta.
public class PlantaTarifa {
    private int id_planta;
    private int tarifa_planta;

    public int getTarifa_planta() {
        return tarifa_planta;
    }

    public void setTarifa_planta(int tarifa_planta) {
        this.tarifa_planta = tarifa_planta;
    }

    public int getId_planta() {
        return id_planta;
    }

    public void setId_planta(int id_planta) {
        this.id_planta = id_planta;
    }
}
