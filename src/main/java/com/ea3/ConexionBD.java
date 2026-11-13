package com.ea3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    public Connection conectar(String servidor, String baseDatos,
            String usuario, String contrasena)
            throws ClassNotFoundException, SQLException {

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        String url = "jdbc:sqlserver://" + servidor
                + ";databaseName=" + baseDatos
                + ";encrypt=true"
                + ";trustServerCertificate=true";

        if (usuario.isEmpty() && contrasena.isEmpty()) {
            url += ";integratedSecurity=true";
            return DriverManager.getConnection(url);
        } else {
            return DriverManager.getConnection(url, usuario, contrasena);
        }
    }
}