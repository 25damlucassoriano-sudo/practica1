package com.lucassorianoaisa.tvideojuegos.util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class Util {
    public static void MensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null,
                mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static int mensajeConfirmacion(String mensaje, String titulo) {
        return JOptionPane.showConfirmDialog(null, mensaje,
                titulo, JOptionPane.YES_NO_CANCEL_OPTION);
    }

    public static JFileChooser crearSelectorFichero(File rutaDefecto, String tipoArchivos, String extension) {
        JFileChooser selectorFichero = new JFileChooser();
        if (rutaDefecto!=null) {
            selectorFichero.setCurrentDirectory(rutaDefecto);
        }
        if (extension!=null) {
            FileNameExtensionFilter filtro = new
                    FileNameExtensionFilter(tipoArchivos,extension);
            selectorFichero.setFileFilter(filtro);
        }
        return selectorFichero;
    }
}
