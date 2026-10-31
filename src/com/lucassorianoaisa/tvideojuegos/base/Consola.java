package com.lucassorianoaisa.tvideojuegos.base;

import java.time.LocalDate;

public class Consola extends Producto{
    //Atributo
    private int espacio;

    //Cosntructores
    public Consola() {
        super();
    }

    public Consola(boolean usado, String plataforma, Double precio, LocalDate fechaSalida, int espacio) {
        super(usado, plataforma, precio, fechaSalida);
        this.espacio = espacio;
    }

    //Setter y Getter
    public int getEspacio() {
        return espacio;
    }

    public void setEspacio(int espacio) {
        this.espacio = espacio;
    }

    //El toString para poder recibirlo como cadena de texto
    @Override
    public String toString() {
        return "Consola: " + getPlataforma() + "(" + getEspacio() + "gb)";
    }
}
