package com.ea3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VentanaConexion extends JFrame {

    private final JTextField txtServidor = new JTextField("localhost");
    private final JTextField txtBaseDatos = new JTextField("EA3_ConexionBD");
    private final JTextField txtUsuario = new JTextField();
    private final JPasswordField txtContrasena = new JPasswordField();

    private final JTextArea txtResultados = new JTextArea();
    private final JButton btnConectar = new JButton("Probar conexión");

    public VentanaConexion() {
        configurarVentana();
        construirInterfaz();
        configurarEventos();
    }

    private void configurarVentana() {
        setTitle("EA3 - Conexión Java con SQL Server");
        setSize(650, 470);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void construirInterfaz() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel(
                "CONFIGURACIÓN DE CONEXIÓN A BASE DE DATOS",
                SwingConstants.CENTER
        );

        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 10, 10));

        panelCampos.add(new JLabel("Servidor:"));
        panelCampos.add(txtServidor);

        panelCampos.add(new JLabel("Base de datos:"));
        panelCampos.add(txtBaseDatos);

        panelCampos.add(new JLabel("Usuario:"));
        panelCampos.add(txtUsuario);

        panelCampos.add(new JLabel("Contraseña:"));
        panelCampos.add(txtContrasena);

        txtResultados.setEditable(false);
        txtResultados.setLineWrap(true);
        txtResultados.setWrapStyleWord(true);
        txtResultados.setText("Esperando prueba de conexión...");

        JScrollPane scroll = new JScrollPane(txtResultados);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));

        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.add(panelCampos, BorderLayout.NORTH);
        panelCentral.add(scroll, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnConectar);

        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void configurarEventos() {
        btnConectar.addActionListener(e -> probarConexion());
    }

    private void probarConexion() {

        String servidor = txtServidor.getText().trim();
        String baseDatos = txtBaseDatos.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (servidor.isEmpty() || baseDatos.isEmpty()) {
            mostrarError("Debe completar el servidor y la base de datos.");
            return;
        }

        txtResultados.setForeground(Color.BLUE);
        txtResultados.setText("Intentando conectar con SQL Server...");

        try (Connection conexion = new ConexionBD().conectar(
                servidor, baseDatos, usuario, contrasena)) {

            txtResultados.setForeground(new Color(0, 128, 0));

            String autenticacion = usuario.isEmpty()
                    && contrasena.isEmpty()
                    ? "Windows" : "SQL Server";

            txtResultados.setText(
                    "¡CONEXIÓN EXITOSA!\n\n"
                    + "Servidor: " + servidor + "\n"
                    + "Base de datos: " + baseDatos + "\n"
                    + "Autenticación: " + autenticacion + "\n"
                    + "Estado: Conectado correctamente."
            );

        } catch (ClassNotFoundException ex) {

            mostrarError(
                    "No se encontró el controlador JDBC.\n"
                    + ex.getMessage()
            );

        } catch (SQLException ex) {

            mostrarError(
                    "ERROR DE CONEXIÓN JDBC\n\n"
                    + "Código: " + ex.getErrorCode() + "\n"
                    + "Estado SQL: " + ex.getSQLState() + "\n"
                    + "Descripción: " + ex.getMessage()
            );
        }
    }

    private void mostrarError(String mensaje) {
        txtResultados.setForeground(Color.RED);
        txtResultados.setText(mensaje);
    }
}