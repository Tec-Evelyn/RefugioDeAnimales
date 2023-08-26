<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="refugiodeanimales.entidadesdenegocio.Tipo"%>
<% Tipo tipo = (Tipo) request.getAttribute("tipo");%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalles del Contacto</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalles del Tipo</h5>
            <div class="row">
                
                <div class="input-field col l4 s12">
                    <input disabled  id="txtNombre" type="text" value="<%=contacto.getNombre()%>">
                    <label for="txtNombre">Nombre</label>
                </div>                                     
            </div>
                    
            <div class="row">
                <div class="col l12 s12">
                    <a href="Tipo?accion=edit&id=<%=tipo.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>                        
                    <a href="Tipo" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                </div>
            </div>  
                    
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>