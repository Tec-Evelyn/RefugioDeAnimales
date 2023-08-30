<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="refugiodeanimales.entidadesdenegocio.Mascota"%>
<% Mascota mascota = (Mascota) request.getAttribute("mascota");%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Mascota</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Mascota</h5>
            <form action="Mascota" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=mascota.getId()%>">  
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtRaza" type="text" name="raza" value="<%=mascota.getRaza()%>" required class="validate" maxlength="50">
                        <label for="txtRaza">Raza</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" value="<%=mascota.getNombre()%>" required class="validate" maxlength="30">
                        <label for="txtNombre">Nombre</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtImagenUrl" type="text" name="imagenurl" value="<%=mascota.getImagenurl()%>" required  class="validate" maxlength="25">
                        <label for="txtImagenUrl">ImagenUrl</label>
                    </div>
                    
                    <div class="input-field col l4 s12">   
                        <select id="slEstatus" name="estatus" class="validate">
                            <option value="0" <%=(mascota.getEstatus() == 10) ? "selected" : ""%>>SELECCIONAR</option>
                            <option value="<%=Mascota.EstatusMascota.ACTIVO%>"  <%=(mascota.getEstatus() == Mascota.EstatusMascota.ACTIVO) ? "selected" : ""%>>ACTIVO</option>
                            <option value="<%=Mascota.EstatusMascota.INACTIVO%>"  <%=(mascota.getEstatus() == Mascota.EstatusMascota.INACTIVO) ? "selected" : ""%>>INACTIVO</option>
                        </select>       
                        <label for="slEstatus">Estatus</label>
                        <span id="slEstatus_error" style="color:red" class="helper-text"></span>
                    </div>
                    
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Tipo/select.jsp">                           
                            <jsp:param name="id" value="<%=mascota.getIdTipo() %>" />  
                        </jsp:include>  
                        <span id="slTipo_error" style="color:red" class="helper-text"></span>
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Genero/select.jsp">                           
                            <jsp:param name="id" value="<%=mascota.getIdGenero() %>" />  
                        </jsp:include>  
                        <span id="slGenero_error" style="color:red" class="helper-text"></span>
                    </div>
                </div>

                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Mascota" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
                        
        <jsp:include page="/Views/Shared/footerBody.jsp" />   
        <script>
            function validarFormulario() {
                var result = true;                
                var slTipo = document.getElementById("slTipo");
                var slTipo_error = document.getElementById("slTipo_error");
                if (slTipo.value == 0) {
                    slTipo_error.innerHTML = "El Tipo es obligatorio";
                    result = false;
                } else {
                    slTipo_error.innerHTML = "";
                }
                
                var slGenero = document.getElementById("slGenero");
                var slGenero_error = document.getElementById("slGenero_error");
                if (slGenero.value == 0) {
                    slGenero_error.innerHTML = "El Genero es obligatorio";
                    result = false;
                } else {
                    slGenero_error.innerHTML = "";
                }

                return result;
            }
        </script>
    </body>
</html>

