package refugiodeanimales.appweb.controllers;

import java.io.IOException;
//import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import refugiodeanimales.accesoadatos.TipoDAL;
import refugiodeanimales.entidadesdenegocio.Tipo;
import refugiodeanimales.appweb.utils.*;

@WebServlet(name = "TipoServlet", urlPatterns = {"/Tipo"})
public class TipoServlet extends HttpServlet {
    
    
}
