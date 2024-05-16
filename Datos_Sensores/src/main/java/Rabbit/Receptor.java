/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Rabbit;

import DominioDatos.Datos;
import com.mycompany.dao_sensores.DatosDAO;
import java.io.IOException;
import com.rabbitmq.client.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

/**
 *
 * @author oscar
 *
 */
public class Receptor {

    private static final String EXCHANGE_NAME = "colaDatos";
    private static final String ROUTING_KEY = "key1";
    private static final String RABBITMQ_HOST = "localhost";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY = "B/lUwQC\"`nI8Ze>+eI~qLSWEwbE`?Zt~"; // La misma clave

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RABBITMQ_HOST);

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.queueDeclare(EXCHANGE_NAME, false, false, false, null);
            String prueba = channel.queueDeclare().getQueue();
            channel.queueBind(prueba, EXCHANGE_NAME, ROUTING_KEY);
            System.out.println(" [*] Esperando mensajes en" + EXCHANGE_NAME + "Para salir, presion CRTL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                try {
                    String encryptedMessage = new String(delivery.getBody(), "UTF-8");
                    String decryptedMessage = DesencriptarSocket.desencriptar(encryptedMessage);
                    System.out.println("Llego Mensaje Desencriptado: " + decryptedMessage);
                    // Ahora puedes procesar los datos desencriptados
                    DatosDAO datosDAO = new DatosDAO("localhost", "3306", "datossensores", "root", "12345");
                    Datos datos = new Datos();
                    String[] atributos = decryptedMessage.split(":");
                    datos.setIdSensor(atributos[0]);
                    datos.setTipoSensor("Sensor");
                    datos.setMedidaHumedad(Double.parseDouble(atributos[2]));
                    datos.setMedidaTemperatura(Double.parseDouble(atributos[3]));
                    datos.setFechaHora(LocalDateTime.now());
                    datos.setMarcaSensor(atributos[1]);
                    datosDAO.agregarDatos(datos);
                } catch (Exception e) {
                    System.out.println("Error al desencriptar y procesar el mensaje: " + e.getMessage());
                }
            };
            channel.basicConsume(prueba, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
