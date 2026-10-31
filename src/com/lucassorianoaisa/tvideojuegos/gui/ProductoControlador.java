package com.lucassorianoaisa.tvideojuegos.gui;

import com.lucassorianoaisa.tvideojuegos.base.Accesorio;
import com.lucassorianoaisa.tvideojuegos.base.Consola;
import com.lucassorianoaisa.tvideojuegos.base.Juego;
import com.lucassorianoaisa.tvideojuegos.base.Producto;
import com.lucassorianoaisa.tvideojuegos.util.Util;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import static com.sun.java.accessibility.util.SwingEventMonitor.addListSelectionListener;

public class ProductoControlador implements ActionListener, ListSelectionListener, WindowListener {
    private Ventana vista;
    private ProductoModelo modelo;
    private File ultimaRutaExportada;

    public ProductoControlador(Ventana vista, ProductoModelo modelo) throws IOException {
        this.vista = vista;
        this.modelo = modelo;
        try {
            cargarDatosConfiguracion();
        } catch (IOException e) {
            System.out.println("no existe el fichero de configuracion" + e.getMessage());
        }
        addActionListener(this);
        addListSelectionListener(this);
        addWindowListener(this);
    }

    public ProductoControlador() {

    }

    private boolean hayCamposVacios() {
        if (vista.precioTxt.getText().isEmpty() ||
        vista.fechaSalidaDPicker.getText().isEmpty()) {
            return true;
        }
        return false;
    }

    private void limpiarCampos() {
        vista.precioTxt.setText(null);
        vista.fechaSalidaDPicker.setText(null);
    }

    public void refrescar() {
        vista.dlmproducto.clear();
        for (Producto unProducto: modelo.obtenerProductos()) {
            vista.dlmproducto.addElement(unProducto);
        }
    }

    private void addActionListener(ActionListener listener) {
        vista.nuevoButton.addActionListener(listener);
        vista.exportarButton.addActionListener(listener);
        vista.importarButton.addActionListener(listener);
        vista.consolaRadioButton.addActionListener(listener);
        vista.videojuegoRadioButton.addActionListener(listener);
        vista.accesorioRadioButton.addActionListener(listener);
    }

    private void addWindowsListener (WindowListener listener) {
        vista.frame.addWindowListener(listener);
    }

    private void addListSelectionListener (ListSelectionListener listener) {
        vista.list1.addListSelectionListener(listener);
    }

    private void cargarDatosConfiguracion() throws IOException {
        Properties configuracion = new Properties();
        configuracion.load(new FileReader("tienda.conf"));
        ultimaRutaExportada = new File (configuracion.getProperty("ultimaRutaExportada"));
    }

    private void actualizarDatosConfiguracion(File ultimaRutaExportada) {
        this.ultimaRutaExportada = ultimaRutaExportada;
    }


    //implementados automaticamente
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Nuevo":
                if (hayCamposVacios()) {
                    Util.MensajeError("Los campos no pueden estar vacios");
                }
                if (vista.consolaRadioButton.isSelected()) {
                    if (vista.consolaRadioButton.isSelected()) {
                        modelo.altaConsola(
                                vista.usadoRadioButton.isSelected(),
                                vista.plataformaTxt.getText(),
                                Double.parseDouble(vista.precioTxt.getText()),
                                vista.fechaSalidaDPicker.getDate(),
                                Integer.parseInt(vista.extraTxt.getText()));

                    } else if (vista.videojuegoRadioButton.isSelected()) {
                        modelo.altaJuego(
                                vista.usadoRadioButton.isSelected(),
                                vista.plataformaTxt.getText(),
                                Double.parseDouble(vista.precioTxt.getText()),
                                vista.fechaSalidaDPicker.getDate(),
                                vista.extraTxt.getText());

                    } else {
                        modelo.altaAccesorio(
                                vista.usadoRadioButton.isSelected(),
                                vista.plataformaTxt.getText(),
                                Double.parseDouble(vista.precioTxt.getText()),
                                vista.fechaSalidaDPicker.getDate(),
                                vista.extraTxt.getText());
                    }
                }
                limpiarCampos();
                refrescar();
                break;

            case "Importar":
                JFileChooser selectorFichero = Util.crearSelectorFichero(ultimaRutaExportada,
                        "Archivos XML", "xml");
                int opcion = selectorFichero.showOpenDialog(null);
                if (opcion == JFileChooser.APPROVE_OPTION) {
                    try {
                        modelo.importarXML(selectorFichero.getSelectedFile());
                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    }
                    refrescar();
                }
                break;

            case "Exportar":
                JFileChooser selectorFichero2 = Util.crearSelectorFichero(ultimaRutaExportada,
                        "Archivos XML","xml");
                int opcion2 = selectorFichero2.showSaveDialog(null);
                if (opcion2==JFileChooser.APPROVE_OPTION) {
                    try {
                        modelo.exportar(selectorFichero2.getSelectedFile());
                        actualizarDatosConfiguracion(selectorFichero2.getSelectedFile());
                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (TransformerException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "Consola":
                vista.extraLbl.setText("Espacio");
                break;
            case "Juego":
                vista.extraLbl.setText("Nombre");
                break;
            case "Accesorio":
                vista.extraLbl.setText("Tipo");
                break;
        }
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        int resp = Util.mensajeConfirmacion("¿Desea cerrar la ventana?", "Salir");
        if (resp == JOptionPane.OK_OPTION) {
            try {
                guardarConfiguracion();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }

    private void guardarConfiguracion() throws IOException {
        Properties configuracion = new Properties();
        configuracion.setProperty("ultimaRutaExportada", ultimaRutaExportada.getAbsolutePath());
        configuracion.store(new PrintWriter
                ("tienda.conf"), "Datos configuracion vehiculos");
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            Producto productoSeleccionado = (Producto) vista.list1.getSelectedValue();
            vista.plataformaTxt.setText(productoSeleccionado.getPlataforma());
            vista.precioTxt.setText(String.valueOf(productoSeleccionado.getPrecio()));
            // Comprobar si el producto es usado o nuevo
            if (productoSeleccionado.isUsado()) {
                vista.usadoRadioButton.setSelected(true);
                vista.nuevoRadioButton.setSelected(false);
            } else {
                vista.usadoRadioButton.setSelected(false);
                vista.nuevoRadioButton.setSelected(true);
            }
            vista.fechaSalidaDPicker.setDate(productoSeleccionado.getFechaSalida());
            if (productoSeleccionado instanceof Consola) {
                vista.extraTxt.setText(String.valueOf(((Consola) productoSeleccionado).getEspacio()));
                vista.consolaRadioButton.doClick();
            } else if (productoSeleccionado instanceof Juego) {
                vista.extraTxt.setText(String.valueOf(((Juego) productoSeleccionado).getNombre()));
                vista.videojuegoRadioButton.doClick();
            } else {
                vista.extraTxt.setText(String.valueOf(((Accesorio) productoSeleccionado).getTipo()));
                vista.accesorioRadioButton.doClick();
            }
        }
    }
}
