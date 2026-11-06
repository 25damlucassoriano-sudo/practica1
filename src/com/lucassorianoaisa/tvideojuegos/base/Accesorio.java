package com.lucassorianoaisa.tvideojuegos.base;

import java.time.LocalDate;

public class Accesorio extends Producto {
    //Atributo
    private String Tipo;

    //Construcotres
    public Accesorio() {
        super();
    }

    public Accesorio(boolean usado, String plataforma, Double precio, LocalDate fechaSalida, String tipo) {
        super(usado, plataforma, precio, fechaSalida);
        Tipo = tipo;
    }

    //Setter y Getter
    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    //toString
    @Override
    public String toString() {
        return "Accesorio: " + Tipo + "por: " + getPrecio() + " para:" + getPlataforma();
    }
}
