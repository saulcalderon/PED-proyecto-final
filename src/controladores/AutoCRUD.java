package controladores;

import entidades.Auto;
import entidades.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author saul
 */
public class AutoCRUD {

    private Connection conexion;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Statement statement;
    public static String[] autoColumnas = {"ID", "Modelo", "Marca", "Año"};

    public boolean agregarAuto(Auto auto) {

        try {
            String sql = null;
            conexion = this.obtenerConexion();

            sql = "INSERT INTO auto (modelo, marca, anio) VALUES (?,?,?)";
            preparedStatement = conexion.prepareStatement(sql);

            preparedStatement.setString(1, auto.getModelo());
            preparedStatement.setString(2, auto.getMarca());
            preparedStatement.setInt(3, auto.getAnio());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar un alumno: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarAuto(Auto auto) {
        try {
            String sql = null;
            conexion = this.obtenerConexion();

            sql = "UPDATE auto SET modelo = ?, marca = ?, anio = ? WHERE id = ?";
            preparedStatement = conexion.prepareStatement(sql);

            preparedStatement.setString(1, auto.getModelo());
            preparedStatement.setString(2, auto.getMarca());
            preparedStatement.setInt(3, auto.getAnio());

            preparedStatement.setInt(4, auto.getId());

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean eliminarAuto(Integer id) {
        try {
            String sql = null;
            conexion = this.obtenerConexion();

            sql = "DELETE FROM auto WHERE id = ?";
            preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar un auto: " + e.getMessage());
            return false;
        }

    }

    public DefaultTableModel seleccionarAutos() {
        ArrayList<Auto> autos = new ArrayList<>();
        try {
            String sql = null;
            conexion = this.obtenerConexion();

            sql = "SELECT id, modelo, marca, anio FROM auto";
            preparedStatement = conexion.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Auto auto = new Auto();
                auto.setId(resultSet.getInt(1));
                auto.setModelo(resultSet.getString(2));
                auto.setMarca(resultSet.getString(3));
                auto.setAnio(resultSet.getInt(4));
                autos.add(auto);
            }

            if (autos.isEmpty()) {
                DefaultTableModel defaultModel = new DefaultTableModel();
                defaultModel.setColumnIdentifiers(autoColumnas);
                return defaultModel;
            }

            DefaultTableModel dtmAutos = new DefaultTableModel();

            dtmAutos.setColumnIdentifiers(autoColumnas);
            for (int i = 0; i < autos.size(); i++) {

                dtmAutos.addRow(new Object[]{autos.get(i).getId(),
                    autos.get(i).getModelo(), autos.get(i).getMarca(), autos.get(i).getAnio(),});
            }

            return dtmAutos;

        } catch (SQLException e) {
            System.out.println("ERROR:" + e.getMessage());
            DefaultTableModel defaultModel = new DefaultTableModel();
            defaultModel.setColumnIdentifiers(autoColumnas);
            return defaultModel;

        }

    }

    private Connection obtenerConexion() throws SQLException {
        Conexion conn = new Conexion();
        return conn.obtenerConexion();
    }
}
