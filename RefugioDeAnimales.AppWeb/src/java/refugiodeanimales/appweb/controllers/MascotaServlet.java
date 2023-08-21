package refugiodeanimales.appweb.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import refugiodeanimales.accesoadatos.TipoDAL;
import refugiodeanimales.accesoadatos.GeneroDAL;
import refugiodeanimales.accesoadatos.MascotaDAL;
import refugiodeanimales.appweb.utils.*;
import refugiodeanimales.entidadesdenegocio.Tipo;
import refugiodeanimales.entidadesdenegocio.Genero;
import refugiodeanimales.entidadesdenegocio.Mascota;

@WebServlet(name = "MascotaServlet", urlPatterns = {"/Mascota"})
public class MascotaServlet extends HttpServlet {

    
}
