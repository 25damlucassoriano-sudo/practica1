package com.lucassorianoaisa.tvideojuegos.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.lucassorianoaisa.tvideojuegos.base.Producto;
import java.util.Random;

import javax.swing.*;
import java.awt.*;

public class Ventana {
    public JPanel panel1;
    public JLabel tipoProducto;
    public JRadioButton consolaRadioButton;
    public JRadioButton videojuegoRadioButton;
    public JRadioButton accesorioRadioButton;
    public JRadioButton nuevoRadioButton;
    public JRadioButton usadoRadioButton;
    public JLabel segundaMano;
    public JTextField precioTxt;
    public JButton nuevoButton;
    public JButton exportarButton;
    public JButton importarButton;
    public JList list1;
    public DatePicker fechaSalidaDPicker;
    public JLabel extraLbl;
    public JTextField extraTxt;
    public JLabel precioProducto;
    public JLabel añoSalida;
    public JLabel plataforma;
    public JTextField plataformaTxt;
    public JButton aleatorioButton;

    //Creado por mi
    public JFrame frame;
    public DefaultListModel<Producto> dlmproducto;


    public Ventana() {
        frame = new JFrame("Productos Modelo Vista Controlador");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        //Center
        frame.setLocationRelativeTo(null);
        initComponentes();
    }

    private void initComponentes() {
        dlmproducto = new DefaultListModel<Producto>();
        list1.setModel(dlmproducto);

        // Agrupar los RadioButton de nuevo y usado
        ButtonGroup grupoEstado = new ButtonGroup();
        grupoEstado.add(nuevoRadioButton);
        grupoEstado.add(usadoRadioButton);

        // Agrupar los RadioButton del tipo de producto
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(consolaRadioButton);
        grupoTipo.add(videojuegoRadioButton);
        grupoTipo.add(accesorioRadioButton);

        // Color de fondo por defecto
        panel1.setBackground(Color.cyan);

        // Generador de números aleatorios
        Random random = new Random();

        // Acción del botón
        aleatorioButton.addActionListener(e -> {

            int rojo = random.nextInt(256);
            int verde = random.nextInt(256);
            int azul = random.nextInt(256);

            Color colorAleatorio = new Color(rojo, verde, azul);

            panel1.setBackground(colorAleatorio);
        });
    }
}
