/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao_sensores;

import DominioDatos.Datos;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;



/**
 *
 * @author oscar
 */
public class prueba {
    public static void main(String[] args) {
        
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(fechaHoraActual);
        // Crear una instancia de DatosDAO con los parámetros de conexión
        DatosDAO datosDAO = new DatosDAO("localhost", "3306", "DatosSensores", "root", "1234");

        // Crear datos de prueba
        Datos datos = new Datos("1", "Sensor de Humedad", 75.5, 25.3, fechaHoraActual, "MarcaXYZ");
        
        // Probar el método agregarDatos()
        try {
            Datos datosInsertados = datosDAO.agregarDatos(datos);
            if (datosInsertados != null) {
                System.out.println("Datos insertados correctamente. ID generado: " + datosInsertados.getId());
            } else {
                System.out.println("Hubo un problema al insertar los datos.");
            }
        } catch (Exception e) {
            System.out.println("Error al agregar datos: " + e.getMessage());
        }
    }
    }

