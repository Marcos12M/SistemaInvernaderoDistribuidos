/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package rabbit;

import com.rabbitmq.client.*;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author diego
 *
 */
public class Receptor {

    private static final String EXCHANGE_NAME = "colaDatos";
    private static final String ROUTING_KEY = "key2"; // Clave de enrutamiento para esta cola
    private static final String RABBITMQ_HOST = "localhost";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY = "B/lUwQC\"`nI8Ze>+eI~qLSWEwbE`?Zt~";

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
                    EnviarColaMensajeria colita = new EnviarColaMensajeria();
                    colita.construirMensaje(decryptedMessage);
                } catch (Exception e) {
                    System.out.println("Error al desencriptar y procesar el mensaje: " + e.getMessage());
                }
            };
            channel.basicConsume(prueba, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

}
