package com.lucassorianoaisa.tvideojuegos;

import com.lucassorianoaisa.tvideojuegos.gui.ProductoControlador;
import com.lucassorianoaisa.tvideojuegos.gui.ProductoModelo;
import com.lucassorianoaisa.tvideojuegos.gui.Ventana;

import java.io.IOException;

public class Principal {
    public static void main(String[] args) throws IOException {
        Ventana vista = new Ventana();
        ProductoModelo modelo = new ProductoModelo();
        ProductoControlador controlador = new ProductoControlador(vista, modelo);
    }
}
