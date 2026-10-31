package com.lucassorianoaisa.tvideojuegos.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.lucassorianoaisa.tvideojuegos.base.Producto;

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

    //Creado por mi
    public JFrame frame;
    public DefaultListModel<Producto> dlmproducto;

    public Ventana() {
        frame = new JFrame("Productos Modelo Vista Controlador");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
    }
}
