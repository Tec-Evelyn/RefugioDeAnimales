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
    
    private Tipo obtenerTipo(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Tipo tipo = new Tipo();
        if (accion.equals("create") == false) {
            tipo.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }

        tipo.setNombre(Utilidad.getParameter(request, "nombre", ""));
        tipo.setEstatus(Byte.parseByte(Utilidad.getParameter(request," estatus", "")));
        //tipo.setEstatus(Utilidad.getParameter(request, "estatus", ""));
        //tipo.setCelular(Utilidad.getParameter(request, "celular", ""));
        
        if (accion.equals("index")) {
            tipo.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            tipo.setTop_aux(tipo.getTop_aux() == 0 ? Integer.MAX_VALUE : tipo.getTop_aux());
        }
        
        return tipo;
    }
    
     private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Tipo tipo = new Tipo();
            tipo.setTop_aux(10);
            ArrayList<Tipo> tipos = TipoDAL.buscar(tipo);
            request.setAttribute("tipos", tipos);
            request.setAttribute("top_aux", tipo.getTop_aux());             
            request.getRequestDispatcher("Views/Tipo/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
     
       private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Tipo tipo = obtenerTipo(request);
            ArrayList<Tipo> tipos = TipoDAL.buscar(tipo);
            request.setAttribute("tipos", tipos);
            request.setAttribute("top_aux", tipo.getTop_aux());
            request.getRequestDispatcher("Views/Tipo/index.jsp").forward(request, response);
        } catch (Exception ex) { 
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
       
      private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Tipo/create.jsp").forward(request, response);
    }
    
     private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Tipo tipo = obtenerTipo(request);
            int result = TipoDAL.crear(tipo);
            if (result != 0) {
                request.setAttribute("accion", "index");
                //doGetRequestIndex : llena la lista con 10 registros
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro registrar un nuevo registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
     
     private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
             
            Tipo tipo = obtenerTipo(request);
            
            Tipo tipo_result = TipoDAL.obtenerPorId(tipo);
            if (tipo_result.getId() > 0) {
                request.setAttribute("tipo", tipo_result);
            } else {
                Utilidad.enviarError("El Id:" + tipo.getId() + " no existe en la tabla de Tipo", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
     private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Tipo/edit.jsp").forward(request, response);
    }
     
      private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Tipo tipo = obtenerTipo(request);
            int result = TipoDAL.modificar(tipo);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
      
       private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Tipo/details.jsp").forward(request, response);
    }
    
    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Tipo/delete.jsp").forward(request, response);
    }
    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Tipo tipo = obtenerTipo(request);
            int result = TipoDAL.eliminar(tipo);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logró eliminar el registro", request, response);
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
                    //En cualquier otro caso => DEFAULT
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }
// </editor-fold>
}
