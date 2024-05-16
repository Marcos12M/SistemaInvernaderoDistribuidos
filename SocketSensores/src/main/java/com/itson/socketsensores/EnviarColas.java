/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.socketsensores;

import java.io.IOException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoshi
 */
public class EnviarColas {

    private static final String EXCHANGE_NAME = "colaDatos";
    ConnectionFactory factory = new ConnectionFactory();
    private static final String RABBITMQ_HOST = "localhost";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY = "B/lUwQC\"`nI8Ze>+eI~qLSWEwbE`?Zt~"; 

    public EnviarColas() {
        factory.setHost(RABBITMQ_HOST);
        
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            // Declara el exchange de tipo "direct"
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            // Cerrar el canal y la conexión
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException ex) {
            System.out.println("Excepción al conectar con RabbitMQ: " + ex.getMessage());
            ex.printStackTrace(); // Imprime la traza de la excepción para obtener más detalles
        }
    }

    public void enviarNotificacionSensor(String compra, String routingKey) throws Exception {
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

            // Encripta los datos
            String compraEncriptada = EncriptarSocket.encriptar(compra);

            // Publica el mensaje en el exchange con la clave de enrutamiento adecuada
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, compraEncriptada.getBytes());
            System.out.println("[x] Sent to exchange '" + EXCHANGE_NAME + "', routing key '" + routingKey + "': '" + compraEncriptada + "'");

        } catch (IOException | TimeoutException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}

//    public EnviarColas() {
//        ConnectionFactory factory = new ConnectionFactory();
//
//        factory.setHost("localhost");
//
//        try {
//            Connection connection = (Connection) factory.newConnection();
//            channel = connection.createChannel();
//        } catch (IOException ex) {
//            Logger.getLogger(EnviarColas.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (TimeoutException ex) {
//            Logger.getLogger(EnviarColas.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//* Investigar si se puede envíar a dos colas
//al mismo tiempo (detector y datos sensores)
//    public void enviarNotificacionSensor(String compra) {
//
//        try {
//
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            channel.basicPublish("", QUEUE_NAME, null, compra.getBytes());
//            System.out.println("[x] Sent '" + compra + "'");
//
//        } catch (Exception ex) {
//
//            System.out.println(ex);
//
//        }
//    }

