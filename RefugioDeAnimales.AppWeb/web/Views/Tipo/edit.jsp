<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="refugiodeanimales.entidadesdenegocio.Tipo"%>
<% Tipo tipo = (Tipo) request.getAttribute("tipo");%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Tipo</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Tipo</h5>
            <form action="Tipo" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">   
                <input type="hidden" name="id" value="<%=tipo.getId()%>">   
                <div class="row">
                    
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" value="<%=tipo.getNombre()%>" required class="validate" maxlength="50">
                        <label for="txtNombre">Nombre</label>
                    </div>                                                                                                       
                </div>
                        
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Tipo" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>