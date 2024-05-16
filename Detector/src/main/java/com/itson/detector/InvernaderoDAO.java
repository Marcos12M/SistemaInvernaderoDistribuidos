/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.detector;

import DominioDetector.Invernadero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hoshi
 */
public class InvernaderoDAO implements IDetector{
    
    private Conexion conexion;

    public InvernaderoDAO() {
        conexion = new Conexion("localhost", "3306", "invernadero", "root", "1234");
    }

    @Override
    public List<Invernadero> colsultar() {
        List<Invernadero> datosList = new ArrayList<>();
        try (Connection connection = conexion.conexion()) {
            String query = "SELECT * FROM invernadero";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Invernadero datos = new Invernadero();
                        datos.setIdInvernadero(rs.getLong("id_invernadero"));
                        //datos.setIdInvernadero(rs.getString("id_invernadero"));
                        datos.setDireccion(rs.getString("direccion"));
                        datos.setNombre(rs.getString("nombre"));
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
