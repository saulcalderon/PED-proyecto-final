package controladores;

import entidades.Alumno;
import entidades.AlumnoAuto;
import entidades.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author saul
 */
public class AlumnoCRUD {

    private Connection conexion;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Statement statement;
    public static String[] nombresColumnas = {"ID", "Nombre", "Identicación", "Dirección", "ID auto", "Marca", "Modelo"};

    public boolean agregarAlumno(Alumno alumno, int autoId) {

        try {
            String sql = null;
            conexion = this.obtenerConexion();

            sql = "INSERT INTO alumno (nombre, identificacion, direccion) VALUES (?,?,?)";
            preparedStatement = conexion.prepareStatement(sql);

            preparedStatement.setString(1, alumno.getNombre());
            preparedStatement.setString(2, alumno.getIdentificacion());
            preparedStatement.setString(3, alumno.getDireccion());

            preparedStatement.executeUpdate();

            sql = "SELECT id FROM alumno ORDER BY id DESC LIMIT 1";
            statement = conexion.createStatement();

            resultSet = statement.executeQuery(sql);

            Integer idAlumno = null;

            while (resultSet.next()) {
                idAlumno = resultSet.getInt(1);
            }

            sql = "INSERT INTO alumno_auto (alumno_id, auto_id) VALUES (?,?)";
            preparedStatement = conexion.prepareStatement(sql);

            preparedStatement.setInt(1, idAlumno);
            preparedStatement.setInt(2, autoId);

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar un alumno: " + e.getMessage());
            return false;
        }
    }

    public boolean desencolar() {
        try {
            String sql = null;
            conexion = this.obtenerConexion();

            sql = "DELETE FROM alumno_auto ORDER BY fecha ASC LIMIT 1";
            preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public DefaultTableModel seleccionarAlumnos() {
        ArrayList<AlumnoAuto> alumnos = new ArrayList<>();
        try {
            String sql = null;
            conexion = this.obtenerConexion();

            sql = "SELECT alumno_id, a.nombre, a.identificacion, a.direccion, auto_id, au.marca, au.modelo FROM alumno_auto aa"
                    + " LEFT JOIN alumno a ON aa.alumno_id = a.id LEFT JOIN auto au ON aa.auto_id = au.id";
            preparedStatement = conexion.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AlumnoAuto alumnoAuto = new AlumnoAuto();
                alumnoAuto.setId(resultSet.getInt(1));
                alumnoAuto.setNombre(resultSet.getString(2));
                alumnoAuto.setIdentificacion(resultSet.getString(3));
                alumnoAuto.setDireccion(resultSet.getString(4));

                alumnoAuto.setAuto_id(resultSet.getInt(5));
                alumnoAuto.setMarca(resultSet.getString(6));
                alumnoAuto.setModelo(resultSet.getString(7));

                alumnos.add(alumnoAuto);
            }

            if (alumnos.isEmpty()) {
                DefaultTableModel defaultModel = new DefaultTableModel();
                defaultModel.setColumnIdentifiers(nombresColumnas);
                return defaultModel;
            }

            DefaultTableModel dtmAlumnos = new DefaultTableModel();

            dtmAlumnos.setColumnIdentifiers(nombresColumnas);
            for (int i = 0; i < alumnos.size(); i++) {

                dtmAlumnos.addRow(new Object[]{alumnos.get(i).getId(),
                    alumnos.get(i).getNombre(), alumnos.get(i).getIdentificacion(), alumnos.get(i).getDireccion(), alumnos.get(i).getAuto_id(),
                    alumnos.get(i).getMarca(), alumnos.get(i).getModelo(),});
            }

            return dtmAlumnos;

        } catch (SQLException e) {
            System.out.println("ERROR:" + e.getMessage());
            DefaultTableModel defaultModel = new DefaultTableModel();
            defaultModel.setColumnIdentifiers(nombresColumnas);
            return defaultModel;

        }

    }

    public DefaultComboBoxModel llenarComboBoxAutos() {

        ArrayList<Integer> autoIds = new ArrayList<>();
        DefaultComboBoxModel dcmAutos = new DefaultComboBoxModel();

        try {
            String sql = null;
            conexion = this.obtenerConexion();

            sql = "SELECT auto_id FROM alumno_auto aa";
            statement = conexion.createStatement();

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                autoIds.add(resultSet.getInt(1));
            }

            if (autoIds.isEmpty()) {
                System.out.println("Something");
                sql = "SELECT id, a.marca, a.modelo FROM auto a";
                statement = conexion.createStatement();

                resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    dcmAutos.addElement(resultSet.getInt(1) + "-" + resultSet.getString(2) + "-" + resultSet.getString(3));
                }

                return dcmAutos;
            }

            StringBuilder sb = new StringBuilder();
            boolean doneOne = false;
            for (Integer str : autoIds) {
                if (doneOne) {
                    sb.append(", ");
                }
                sb.append("").append(str).append("");
                doneOne = true;
            }

            sql = "SELECT id, a.marca, a.modelo FROM auto a WHERE id NOT IN (" + sb.toString() + ")";
            statement = conexion.createStatement();

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                dcmAutos.addElement(resultSet.getInt(1) + "-" + resultSet.getString(2) + "-" + resultSet.getString(3));
            }

            return dcmAutos;

        } catch (SQLException e) {
            System.out.println("Error al cargar los autos disponibles: " + e.getMessage());
            return null;
        }
    }

    private Connection obtenerConexion() throws SQLException {
        Conexion conn = new Conexion();
        return conn.obtenerConexion();
    }
}
