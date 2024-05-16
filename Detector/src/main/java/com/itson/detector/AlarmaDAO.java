/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.detector;

import DominioDetector.Alarma;
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
public class AlarmaDAO implements IDetector{
    
    private Conexion conexion;

    public AlarmaDAO() {
        conexion = new Conexion("localhost", "3306", "invernadero", "root", "1234");
    }

    @Override
    public List<Alarma> colsultar() {
        List<Alarma> datosList = new ArrayList<>();
        try (Connection connection = conexion.conexion()) {
            String query = "SELECT * FROM alarma";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Alarma datos = new Alarma();
                        datos.setIdAlarma(rs.getLong("id_alarma"));
                        //datos.setIdAlarma(rs.getString("id_alarma"));
                        datos.setLimite_humedad(rs.getDouble("limite_humedad"));
                        datos.setLimite_temperatura(rs.getDouble("limite_temperatura"));
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
