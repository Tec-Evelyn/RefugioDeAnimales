<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlproductos.entidadesdenegocio.*"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Mascota> mascotas = (ArrayList<Mascota>) request.getAttribute("mascotas");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (mascotas == null) {
        mascotas = new ArrayList();
    } else if (mascotas.size() > numReg) {
        double divNumPage = (double) mascotas.size() / (double) numReg;
        numPage = (int) Math.ceil(divNumPage);
    }
    String strTop_aux = request.getParameter("top_aux");
    int top_aux = 10;
    if (strTop_aux != null && strTop_aux.trim().length() > 0) {
        top_aux = Integer.parseInt(strTop_aux);
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Lista de Mascotas</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Buscar Mascota</h5>
            <form action="Mascota" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" >
                        <label for="txtNombre">Nombre</label>
                    </div>     
                    
                      <div class="input-field col l4 s12">
                        <input  id="txtRaza" type="number" name="raza" >
                        <label for="txtRaza"></label>
                    </div>
                     <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="number" name="nombre" >
                        <label for="txtNombre">Nombre</label>
                    </div>
                      <div class="input-field col l4 s12">
                        <input  id="txtImagenUrl" name="imagenurl" >
                        <label for="txtImagenUrl">Imagen</label>
                    </div>
                     <div class="input-field col l4 s12">
                        <input  id="txtEstatus" type="number" name="estatus" >
                        <label for="txtEstatus">Estatus</label>
                    </div>

  
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Tipo/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                        <span id="slTipo_error" style="color:red" class="helper-text"></span>
                    </div>
                    
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Genero/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                        <span id="slGenero_error" style="color:red" class="helper-text"></span>
                    </div>
                    
                   
                    
                                      
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Shared/selectTop.jsp">
                            <jsp:param name="top_aux" value="<%=top_aux%>" />                        
                        </jsp:include>                        
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">search</i>Buscar</button>
                        <a href="Mascota?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>

            <div class="row">
                <div class="col l12 s12">
                    <div style="overflow: auto">
                        <table class="paginationjs">
                            <thead>
                                <tr>                                     
                                    <th>Raza</th>  
                                    <th>Nombre</th> 
                                    <th>ImagenUrl</th>  
                                    <th>Estatus</th>       
                                    <th>Acciones</th>
                                </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (Mascota mascota : mascotas) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                %>
                                <tr data-page="<%= tempNumPage%>">                                    
                                    <td><%=mascota.getRaza()%></td>  
                                    <td><%=mascota.getNombre()%></td>
                                    <td><%=mascota.getImagenUrl()%></td>  
                                    <td><%=mascota.getEstatus()%></td>                
                                    <td><%=mascota.getTipo().getNombre()%></td>  
                                    <td><%=mascota.getGenero().getNombre()%></td>
                                    
                                    <td>
                                        <div style="display:flex">
                                             <a href="Mascota?accion=edit&id=<%=mascota.getId()%>" title="Modificar" class="waves-effect waves-light btn green">
                                            <i class="material-icons">edit</i>
                                        </a>
                                        <a href="Mascota?accion=details&id=<%=mascota.getId()%>" title="Ver" class="waves-effect waves-light btn blue">
                                            <i class="material-icons">description</i>
                                        </a>
                                        <a href="Mascota?accion=delete&id=<%=mascota.getId()%>" title="Eliminar" class="waves-effect waves-light btn red">
                                            <i class="material-icons">delete</i>
                                        </a>    
                                        </div>                                                                    
                                    </td>                                   
                                </tr>
                                <%}%>                                                       
                            </tbody>
                        </table>
                    </div>                  
                </div>
            </div>             
            <div class="row">
                <div class="col l12 s12">
                    <jsp:include page="/Views/Shared/paginacion.jsp">
                        <jsp:param name="numPage" value="<%= numPage%>" />                        
                    </jsp:include>
                </div>
            </div>
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>
