package com.lucassorianoaisa.tvideojuegos.base;

import java.time.LocalDate;

public class Juego extends Producto{
    //Atributos
    private String nombre;

    //Constructores
    public Juego() {
        super();
    }

    public Juego(boolean usado, String plataforma, Double precio, LocalDate fechaSalida, String nombre) {
        super(usado, plataforma, precio, fechaSalida);
        this.nombre = nombre;
    }

    //Setters y Getters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //toString
    @Override
    public String toString() {
        return "Juego: " + getNombre() + " para " + getPlataforma();
    }
}
