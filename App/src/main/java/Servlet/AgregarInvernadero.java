/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;


import Controlador.ControladorInvernadero;
import Controlador.ManagerInvernadero;
import dominio.Invernadero;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author diego
 */
    @WebServlet(name = "AgregarInvernadero", urlPatterns = {"/AgregarInvernadero"})
public class AgregarInvernadero extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AgregarInvernadero</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AgregarInvernadero at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    private boolean verificarConexionDB() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://mysql:3306/invernadero", "root", "1234")) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Obtener los parámetros del formulario

        String direccion = request.getParameter("direccion");
        String nombre = request.getParameter("nombre");

        // Crear una nueva instancia de Invernadero con los datos recibidos
        Invernadero invernadero = new Invernadero(direccion, nombre);

        // Crear el EntityManagerFactory y EntityManager
        ManagerInvernadero manager = new ManagerInvernadero();
        manager.agregarInvernadero(invernadero);
       

        // Redirigir de vuelta al menú principal
        response.sendRedirect("index.html");
    }


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
