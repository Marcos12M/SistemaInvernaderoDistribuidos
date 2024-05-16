/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.detector;


import DominioDetector.Sensor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hoshi
 */
public class SensorDAO implements IDetector{
    
    private Conexion conexion;

    public SensorDAO() {
        conexion = new Conexion("localhost", "3306", "invernadero", "root", "1234");
    }

    @Override
    public List<Sensor> colsultar() {
       List<Sensor> datosList = new ArrayList<>();
        try (Connection connection = conexion.conexion()) {
            String query = "SELECT * FROM sensor";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Sensor datos = new Sensor();
                        datos.setIdSensor(rs.getLong("id_sensor"));
                        //datos.setIdSensor(rs.getString("id_sensor"));
                        datos.setClave_sensor(rs.getString("clave_sensor"));
                        datos.setMarca(rs.getString("marca"));
                        datos.setId_invernadero(rs.getLong("id_invernadero"));
                        datos.setId_alarma(rs.getLong("id_alarma"));
                        datosList.add(datos);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datosList;
    }
    
    
    
}
