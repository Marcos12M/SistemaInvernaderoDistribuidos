/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import dominio.Alarma;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class ManagerAlarma {

    private String url = "jdbc:mysql://localhost:3306/invernadero";
    private String user = "root";
    private String password = "1234";

    public ManagerAlarma() { 
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver MySQL: " + e.getMessage());
            return null;
        }
        return DriverManager.getConnection(url, user, password);
    }

     public void agregarAlarmaYActualizarSensor(Alarma alarma, long idSensor) {
        String callProcedure = "{CALL agregar_alarma_y_actualizar_sensor(?, ?, ?)}";
        try (Connection connection = getConnection();
             CallableStatement callStmt = connection.prepareCall(callProcedure)) {
            
            callStmt.setDouble(1, alarma.getLimiteHumedad());
            callStmt.setDouble(2, alarma.getLimiteTemperatura());
            callStmt.setLong(3, idSensor);

            callStmt.execute();
            System.out.println("Se agregó la alarma y se actualizó el sensor correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public void actualizarAlarma(Alarma alarma) {
//        String query = "UPDATE alarma SET limite_humedad = ?, limite_temperatura = ?, id_sensor = ? WHERE id_alarma = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setDouble(1, alarma.getLimiteHumedad());
//            preparedStatement.setDouble(2, alarma.getLimiteTemperatura());
//            preparedStatement.setLong(3, alarma.getSensor());
//            preparedStatement.setLong(4, alarma.getIdAlarma());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void eliminarAlarma(Long id) {
        String query = "DELETE FROM alarma WHERE id_alarma = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Alarma obtenerAlarmaPorId(Long id) {
        String query = "SELECT * FROM alarma WHERE id_alarma = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Alarma(
                    resultSet.getLong("id_alarma"),
                    resultSet.getDouble("limite_humedad"),
                    resultSet.getDouble("limite_temperatura")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Alarma> obtenerTodasLasAlarmas() {
        List<Alarma> alarmas = new ArrayList<>();
        String query = "SELECT * FROM alarma";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Alarma alarma = new Alarma(
                    resultSet.getLong("id_alarma"),
                    resultSet.getDouble("limite_humedad"),
                    resultSet.getDouble("limite_temperatura")
                );
                alarmas.add(alarma);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alarmas;
    }
}
