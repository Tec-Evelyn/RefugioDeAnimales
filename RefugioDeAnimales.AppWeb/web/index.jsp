<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="refugiodeanimales.appweb.utils.*"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>

<% if (SessionUser.isAuth(request) == false) {
         response.sendRedirect("Usuario?accion=login");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Principal</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container"> 
            <div class="row">
                <div class="col l12 s12">
                    <h1>¡Bienvenidos</h1> <h4>al lugar para adoptar mascotas!</h4>
                    <!--<h4>Un nuevo Hogar</h4>
                    
                    <p>El propósito de este servicio online es dar un nuevo hogar a los animales que  
                    se encuentran sin hogar, abandonados, perdidos o simplemente sus dueños ya no lo
                    quieren tener con ellos. Este 
                    sitio tiene la posibilidad de que personas interesadas en optar a una mascota que le guste 
                    puedan hacerlo de una manera sencilla y rápida. Así ayudamos a muchos animales 
                    a tener un refugio seguro y permanente. En su gran mayoría perros y gatos. 
                    </p>-->
                </div>
            </div>            
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>