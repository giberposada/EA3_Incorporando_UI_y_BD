package com.ea3;

import javax.swing.SwingUtilities;

public class EA3_Incorporando_UI_y_BD {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            VentanaConexion ventana = new VentanaConexion();
            ventana.setVisible(true);
        });

    }
}