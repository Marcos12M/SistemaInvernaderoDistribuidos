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
    private static final String EXCHANGE_NAME = "direct_exchange";
    ConnectionFactory factory = new ConnectionFactory();

    public EnviarColas() {
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Declara el exchange de tipo "direct"
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // Declara y vincula la primera cola con clave de enrutamiento "clave1"
            String queueName1 = "colaDatos";
            channel.queueDeclare(queueName1, false, false, false, null);
            channel.queueBind(queueName1, EXCHANGE_NAME, "clave1");

            // Declara y vincula la segunda cola con clave de enrutamiento "clave2"
            String queueName2 = "colaDatos";
            channel.queueDeclare(queueName2, false, false, false, null);
            channel.queueBind(queueName2, EXCHANGE_NAME, "clave2");

        } catch (IOException | TimeoutException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    public void enviarNotificacionSensor(String compra, String routingKey) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Publica el mensaje en el exchange con la clave de enrutamiento adecuada
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, compra.getBytes());
            System.out.println("[x] Sent to exchange '" + EXCHANGE_NAME + "', routing key '" + routingKey + "': '" + compra + "'");

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

