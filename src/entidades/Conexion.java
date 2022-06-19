/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author saul
 */
public class Conexion {

    private Connection conexion;

    public Conexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conexion = DriverManager.getConnection("jdbc:mysql://sql5.freemysqlhosting.net:3306/sql5500668", "sql5500668", "5DJyRNk82N");
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR:No se encontro el driver para establecer conexion con la base de datos: " + e.getMessage());

        } catch (SQLException e) {
            System.out.println("ERROR: Fallo en SQL:" + e.getMessage());
        }
    }

    public Connection obtenerConexion() {
        return conexion;
    }

    public void cerrarConexion() {
        conexion = null;
        if (conexion == null) {
            System.out.println("Conexion Finalizada.");
        }

    }
}
