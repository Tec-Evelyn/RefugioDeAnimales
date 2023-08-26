package refugiodeanimales.appweb.controllers;

import java.io.IOException;
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

    private Mascota obtenerMascota(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Mascota mascota = new Mascota();
        if (accion.equals("create") == false) {
            mascota.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }
        mascota.setRaza(Utilidad.getParameter(request, "raza", ""));
        mascota.setNombre(Utilidad.getParameter(request, "nombre", ""));
        mascota.setImagenurl(Utilidad.getParameter(request, "imagenurl", ""));
        mascota.setIdTipo(Integer.parseInt(Utilidad.getParameter(request, "IdTipo", "0")));
        mascota.setIdGenero(Integer.parseInt(Utilidad.getParameter(request, "idGenero", "0")));
        mascota.setEstatus(Byte.parseByte(Utilidad.getParameter(request, "estatus", "0")));
        
        if (accion.equals("index")) {
            mascota.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            mascota.setTop_aux(mascota.getTop_aux() == 0 ? Integer.MAX_VALUE : mascota.getTop_aux());
        }
        
        return mascota;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Mascota mascota = new Mascota();
            mascota.setTop_aux(10);
            ArrayList<Mascota> mascotas = MascotaDAL.buscarIncluirRelaciones(mascota);
            request.setAttribute("mascotas", mascotas);
            request.setAttribute("top_aux", mascota.getTop_aux());
            request.getRequestDispatcher("Views/Mascota/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Mascota mascota = obtenerMascota(request);
            ArrayList<Mascota> mascotas = MascotaDAL.buscarIncluirRelaciones(mascota);
            request.setAttribute("mascotas", mascotas);
            request.setAttribute("top_aux", mascota.getTop_aux());
            request.getRequestDispatcher("Views/Mascota/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }       
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Mascota/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Mascota mascota = obtenerMascota(request);
            int result = MascotaDAL.crear(mascota);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro guardar el nuevo registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }

    }

    private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Mascota mascota = obtenerMascota(request);
            Mascota mascota_result = MascotaDAL.obtenerPorId(mascota);
            if (mascota_result.getId() > 0) {
                Tipo tipo = new Tipo();
                tipo.setId(mascota_result.getIdTipo());
                mascota_result.setTipo(TipoDAL.obtenerPorId(tipo));
                request.setAttribute("mascota", mascota_result);
                Genero genero = new Genero();
                genero.setId(mascota_result.getIdGenero());
                mascota_result.setGenero(GeneroDAL.obtenerPorId(genero));
                
                request.setAttribute("mascota", mascota_result);
            } else {
                Utilidad.enviarError("El Id:" + mascota_result.getId() + " no existe en la tabla de Mascota", request, response);
            }
            
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }       
    }

    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Mascota/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Mascota mascota = obtenerMascota(request);
            int result = MascotaDAL.modificar(mascota);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Mascota/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Mascota/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Mascota mascota = obtenerMascota(request);
            int result = MascotaDAL.eliminar(mascota);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro eliminar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doGetRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doGetRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doGetRequestDelete(request, response);
                    break;
                case "details":
                    request.setAttribute("accion", accion);
                    doGetRequestDetails(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doPostRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doPostRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doPostRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doPostRequestDelete(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }
    
// </editor-fold> 
}
