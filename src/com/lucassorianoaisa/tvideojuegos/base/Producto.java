package com.lucassorianoaisa.tvideojuegos.base;

import java.time.LocalDate;

public abstract class Producto {
    // atributos que tendran todos los productos
    private boolean usado;
    private String plataforma;
    private Double precio;
    private LocalDate fechaSalida;

    //Constructores
    public Producto() {
    }

    public Producto(boolean usado, String plataforma, Double precio, LocalDate fechaSalida) {
        this.usado = usado;
        this.plataforma = plataforma;
        this.precio = precio;
        this.fechaSalida = fechaSalida;
    }

    //Setters y Getters para poder ver los atributos y que estos sigan siendo privados
    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
}
