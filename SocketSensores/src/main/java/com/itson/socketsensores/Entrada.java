/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.itson.socketsensores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoshi
 */
public class Entrada implements Runnable {

    private Socket clientSocket;

    public Entrada(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    //Método para envíar a los componentes detector y datos socket
    @Override
    public void run() {
        try {
            PrintWriter out = null;
            BufferedReader in = null;
            //out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Crear una instancia de EnviarColas fuera del bucle
            EnviarColas colitas = new EnviarColas();

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                // Imprime el mensaje recibido desde el cliente
                System.out.println("Mensaje recibido: " + inputLine);

                // Envía el mensaje a la primera cola con clave "clave1"
                colitas.enviarNotificacionSensor(inputLine, "key1");

                // Envía el mensaje a la segunda cola con clave "clave2"
//                colitas.enviarNotificacionSensor(inputLine, "key2");
            }

            //out.close();
            in.close();
            clientSocket.close();

        } catch (IOException ex) {
            Logger.getLogger(Entrada.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Entrada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
